package com.example.myweather.Activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather.R
import com.example.myweather.constants.Constants

class nextDays : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_days)
        sett()
        findViewById<RecyclerView>(R.id.recyclerview2).adapter=ndAdapter(this, Constants.forecast,Constants.werather)
        findViewById<RecyclerView>(R.id.recyclerview2).layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        findViewById<Button>(R.id.back).setOnClickListener {
            val int=Intent(this,MainActivity::class.java)
            startActivity(int)
            this.finish()
        }
    }
    @SuppressLint("SetTextI18n")
    fun sett(){
        val temp = findViewById<TextView>(R.id.temp2)
        val des2 = findViewById<TextView>(R.id.desmain2)
        val wind = findViewById<TextView>(R.id.wind2)
        findViewById<TextView>(R.id.City).text=Constants.werather.name
        val humidity =findViewById<TextView>(R.id.humidity2)
        val clouds =findViewById<TextView>(R.id.clouds2)
        temp.text=Constants.werather.main.temp.toInt().toString()+"\u00B0"
        des2.text=Constants.werather.weather[0].main.toString()
        wind.text = ((Constants.werather.wind.speed*3600)/1000).toInt().toString() + " Km/h"
        humidity.text = Constants.werather.main.humidity.toString() + "%"
        clouds.text = Constants.werather.clouds.all.toString() + "%"
    }
}