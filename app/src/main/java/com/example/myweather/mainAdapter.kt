package com.example.myweather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather.forecastmodel.nfor

class mainAdapter(val context: Context, val forecastweat: nfor): RecyclerView.Adapter<mainAdapter.itemviewholder>() {

    val time= arrayListOf<String>("12 am","3 am","6 am","9 am","12 pm","3 pm","6pm","9pm")

    class itemviewholder(view:View):RecyclerView.ViewHolder(view){
        val deg=view.findViewById<TextView>(R.id.rdegree)
        val time=view.findViewById<TextView>(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemviewholder {
        val layout=LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_main,parent,false)
        return  itemviewholder(layout)
    }

    override fun onBindViewHolder(holder: itemviewholder, position: Int) {
        holder.deg.text=forecastweat.list[position].main.temp.toString()
        holder.time.text=time[position]
    }

    override fun getItemCount(): Int {
        return time.size
    }
}