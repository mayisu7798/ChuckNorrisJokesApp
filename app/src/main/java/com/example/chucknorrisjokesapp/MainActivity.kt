package com.example.chucknorrisjokesapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.*
import android.widget.Button
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity()
{
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Log.d("liste", StaticList.list.toString())
        val recycler: RecyclerView = findViewById(R.id.RecyclerView)
        recycler.layoutManager = LinearLayoutManager(this)
        val listeOfJokes: MutableList<Joke> = mutableListOf()
        //val adapteur = JokeAdapter(StaticList.list.toJokeList())
        val adapteur = JokeAdapter(listeOfJokes)

        val jokeService: JokeApiService = JokeApiServiceFactory.createJokeApiService()
        var joke: Single<Joke> = jokeService.giveMeAJoke()
        var resultSubscribe =
            joke.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onError = { println("Le Single<Joke> renvoie une erreur") },
                    onSuccess = { joke ->   println("Yaaay on peut utiliser la Jooooke : ${joke.value}")
                                            adapteur.setJokes(joke)
                    }
                )
        recycler.adapter = adapteur
        disposable.add(resultSubscribe)

        val button: Button = findViewById(R.id.Button)
        val progressBar: ProgressBar = findViewById(R.id.ProgressBar)
        button.setOnClickListener {
            joke = jokeService.giveMeAJoke()
            resultSubscribe = joke.subscribeOn(Schedulers.io())
                .delay(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .doOnError { println("Le Single<Joke> renvoie une erreur") }
                .doOnSubscribe { progressBar.visibility = VISIBLE }
                .doOnSuccess { joke ->
                    println("Yaaay on peut utiliser la Jooooke : ${joke.value}")
                    adapteur.setJokes(joke)
                }
                .doAfterTerminate { progressBar.visibility = INVISIBLE }
                .subscribe()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
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
