package com.example.chucknorrisjokesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Transformations.map
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
        val adapteur = JokeAdapter(StaticList.list.toJokeList()) // Il faut que je lui donne une liste de Joke
        recycler.adapter = adapteur
    }

    private fun List<String>.toJokeList() : List<Joke> = map { stringJoke ->
        Joke(   categories = listOf("Rien", "Rien bis"),
                createdAt = "Paris",
                iconUrl = "Pas d'Url",
                id = "Pas d'ID",
                updatedAt = "Paris",
                url = "Pas d'Url",
                value = stringJoke )
    }
}
