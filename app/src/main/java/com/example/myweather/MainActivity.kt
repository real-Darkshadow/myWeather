package com.example.myweather

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myweather.databinding.ActivityMainBinding
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ndays.setOnClickListener{
            val int=Intent(this,nextDays::class.java)
            startActivity(int)
        }



//        if(!isLocationEnabled()){
//            Toast.makeText(this, "please turn on your location", Toast.LENGTH_SHORT).show()
//            val intent=Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//            startActivity(intent)
//        }
//        else{
//            Toast.makeText(this, "Location is Enabled", Toast.LENGTH_SHORT).show()
//
//
//        }

    }

    private fun isLocationEnabled():Boolean{
        // provides access to the system location services.
        val locationManager:LocationManager=
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                ||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

}