package com.example.myweather.Activities

import android.content.Context
import android.os.Build
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather.R
import com.example.myweather.forecastmodel.nfor
import com.example.myweather.models.opendata
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class ndAdapter(val context: Context, val forecast: nfor, val weather: opendata) :
    RecyclerView.Adapter<ndAdapter.holder>() {
    class holder(view: View) : RecyclerView.ViewHolder(view) {
        val min = view.findViewById<TextView>(R.id.min2)
        val max = view.findViewById<TextView>(R.id.max2)
        val wi = view.findViewById<ImageView>(R.id.recycler2wi)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holder {
        val li = LayoutInflater.from(parent.context).inflate(R.layout.recycle, parent, false)
        return holder(li)
    }

    override fun onBindViewHolder(holder: holder, position: Int) {

        holder.min.text=forecast.list[position].main.temp_min.toInt().toString()

    }

    override fun getItemCount(): Int {
        return 7
    }

}