package com.example.myweather.Activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather.R
import com.example.myweather.forecastmodel.nfor
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar.AM
import java.util.Date

class mainAdapter(val context: Context, val forecastweat: nfor): RecyclerView.Adapter<mainAdapter.itemviewholder>() {

    val time= arrayListOf<String>("12 am","3 am","6 am","9 am","12 pm","3 pm","6pm","9pm")

    class itemviewholder(view:View):RecyclerView.ViewHolder(view){
        val deg=view.findViewById<TextView>(R.id.rdegree)
        val time=view.findViewById<TextView>(R.id.time)
        val wi2=view.findViewById<ImageView>(R.id.wi2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemviewholder {
        val layout=LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_main,parent,false)
        return  itemviewholder(layout)
    }

    override fun onBindViewHolder(holder: itemviewholder, position: Int) {
        holder.deg.text=forecastweat.list[position].main.temp.toString()
        if(forecastweat.list[position].weather[0].description=="few clouds"){
            holder.wi2.setImageResource(R.drawable.suncloudfastwind)
        }
        else if(forecastweat.list[position].weather[0].id.toString()=="50d"||forecastweat.list[position].weather.last().main=="Haze"){
            holder.wi2.setImageResource(R.drawable.fast_winds)
        }
        else{
            when(forecastweat.list[position].weather.last().main){
                "Clear"->holder.wi2.setImageResource(R.drawable.sun)
                "Clouds"->holder.wi2.setImageResource(R.drawable.cloud)
                "Drizzel"->holder.wi2.setImageResource(R.drawable.cloudmidrain)
                "Rain"->holder.wi2.setImageResource(R.drawable.cloudangledrainzap)
                "Thunderstorm"->holder.wi2.setImageResource(R.drawable.cloud_3_zap)
                "Snow"->holder.wi2.setImageResource(R.drawable.bigsnow)
            }
        }
        val input: SimpleDateFormat = SimpleDateFormat("yyyy-mm-dd hh:mm:ss")
        val output: SimpleDateFormat = SimpleDateFormat("hh:mm aa")
        try {
            val t:Date=input.parse(forecastweat.list[position].dt_txt)
            holder.time.text=output.format(t)
        }catch (e:ParseException){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return 8
    }
}