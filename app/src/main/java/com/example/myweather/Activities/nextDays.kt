package com.example.myweather.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather.R
import com.example.myweather.constants.Constants

class nextDays : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_days)

        val temp = findViewById<TextView>(R.id.temp2)
        val des2 = findViewById<TextView>(R.id.desmain2)
        val wind = findViewById<TextView>(R.id.wind2)
        val humidity =findViewById<TextView>(R.id.humidity2)
        val clouds =findViewById<TextView>(R.id.clouds2)
        findViewById<RecyclerView>(R.id.recyclerview2).adapter=ndAdapter(this, Constants.forecast,Constants.werather)
        findViewById<RecyclerView>(R.id.recyclerview2).layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

    }
}