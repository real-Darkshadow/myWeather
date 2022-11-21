package com.example.myweather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather.forecastmodel.nfor

class mainAdapter(val context: Context): RecyclerView.Adapter<mainAdapter.itemviewholder>() {

    class itemviewholder(view:View):RecyclerView.ViewHolder(view){
        val deg=view.findViewById<TextView>(R.id.rdegree)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemviewholder {
        val layout=LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_main,parent,false)
        return  itemviewholder(layout)
    }

    override fun onBindViewHolder(holder: itemviewholder, position: Int) {
        holder.deg.text="4"
    }

    override fun getItemCount(): Int {
        return 7
    }
}