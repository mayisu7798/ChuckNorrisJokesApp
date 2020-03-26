package com.example.chucknorrisjokesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Log.d("liste", StaticList.list.toString())
        val recycler: RecyclerView = findViewById(R.id.RecyclerView)
        recycler.layoutManager = LinearLayoutManager(this)
        val adapteur = JokeAdapter(StaticList)
        recycler.adapter = adapteur
    }
}
