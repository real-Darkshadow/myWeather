package com.example.myweather
import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.os.SystemClock
import android.provider.CalendarContract.CalendarAlerts
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myweather.constants.Constants
import com.example.myweather.databinding.ActivityMainBinding
import com.example.myweather.models.opendata
import com.example.myweather.neteork.weatherservice
import com.google.android.gms.location.*
import com.google.gson.JsonElement
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var  mFusedLocationClient : FusedLocationProviderClient
    // A global variable for Current Latitude
    private var mLatitude: Double = 0.0
    // A global variable for Current Longitude
    private var mLongitude: Double = 0.0

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if(!islocationenable()){
            Toast.makeText(this, "Location is not enabled", Toast.LENGTH_SHORT).show()
            val intent=Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
        else{
            Dexter.withContext(this)
                .withPermissions(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if (report!!.areAllPermissionsGranted()) {
                            requestLocationData()
                        }

                        if (report.isAnyPermissionPermanentlyDenied) {
                            Toast.makeText(
                                this@MainActivity,
                                "You have denied location permission. Please allow it is mandatory.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        showRationalDialogForPermissions()
                    }
                }).onSameThread()
                .check()

        }

        binding.recyclerView.adapter=mainAdapter(this)
        binding.recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
    }
    lateinit var weatherlist:opendata
     fun getLocationWeatherDetails(lat:String,lon:String){
        if(Constants.isNetworkAvailable(this)){
            val retrofit:Retrofit=Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service:weatherservice=retrofit.create(weatherservice::class.java)
            val listcall: Call<opendata> = service.getweather(
                lat,lon,Constants.APP_ID,Constants.METRIC_UNIT
            )
            listcall.enqueue(object:Callback<opendata>{
                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<opendata>?, response: Response<opendata>?) {

                    if(response!!.isSuccessful){
                        weatherlist=response.body()
                        Log.i("res","$weatherlist")
                        setupui()
                    }
                    else{
                        when(response.code()){
                            400 ->{
                                Log.i("Error 404","Not Found")
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<opendata>?, t: Throwable?) {
                    Log.e("Errorr",t!!.message.toString())
                }

            })


        }
        else{
            Toast.makeText(this, "turn Internet on", Toast.LENGTH_SHORT).show()
        }

    }
    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog,
                                           _ ->
                dialog.dismiss()
            }.show()
    }

    /**
     * A function to request the current location. Using the fused location provider client.
     */
    @SuppressLint("MissingPermission")
    private fun requestLocationData() {

       val  mLocationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,20000).build()

        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }
    /**
     * A location callback object of fused location provider client where we will get the current location details.
     */
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {

            val mLastLocation: Location? = locationResult.lastLocation
            if (mLastLocation != null) {
                mLatitude = mLastLocation.latitude
            }
            Log.e("CurrentLatitude", "$mLatitude")
            if (mLastLocation != null) {
                mLongitude = mLastLocation.longitude
            }
            Log.e("CurrentLongitude", "$mLongitude")
           getLocationWeatherDetails(mLatitude.toString(),mLongitude.toString())
        }
    }

    private fun islocationenable():Boolean{
        val locationmanager:LocationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                ||locationmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    fun setupui(){
        //binding.date.text=
        binding.location.text=weatherlist.name
        binding.temp.text=weatherlist.main.temp.toInt().toString()+"\u00B0"
        binding.desmain.text= weatherlist.weather[0].main
        binding.wind.text=weatherlist.wind.speed.toString()+" m/s"
        binding.humidity.text=weatherlist.main.humidity.toString()+"%"
        binding.rain.text=weatherlist.clouds.all.toString()
        binding.pressure.text=(weatherlist.main.pressure*0.030).toInt().toString()+" inHg"
        binding.max.text=weatherlist.main.temp_max.toInt().toString()
        binding.min.text=weatherlist.main.temp_min.toInt().toString()
        binding.visibility.text=(weatherlist.visibility/1000).toString()+" Km"
        binding.ndays.setOnClickListener{
            val intent=Intent(this,nextDays()::class.java)
            startActivity(intent)
        }

    }
}