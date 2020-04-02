package com.example.chucknorrisjokesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Log.d("liste", StaticList.list.toString())
        val recycler: RecyclerView = findViewById(R.id.RecyclerView)
        recycler.layoutManager = LinearLayoutManager(this)
        val adapteur = JokeAdapter(StaticList.list.toJokeList())
        recycler.adapter = adapteur

        val jokeService : JokeApiService = JokeApiServiceFactory.createJokeApiService()
        val joke : Single<Joke> = jokeService.giveMeAJoke()
        val resultSubscribe = joke.subscribeOn(Schedulers.io()).subscribeBy(
            onError = {println("Le Single<Joke> renvoie une erreur")},
            onSuccess = { joke -> println("Yaaay on peut utiliser la Jooooke : ${joke.value}")}
        )

    }

    private fun List<String>.toJokeList() : List<Joke> = map { stringJoke ->
        Joke(   categories = listOf("Rien", "Rien bis"),
                createdAt = "Paris",
                iconUrl = "Pas d'Url",
                id = "Pas d'ID",
                updatedAt = "Paris",
                url = "Pas d'Url",
                value = stringJoke+"\n" )
    }
}
