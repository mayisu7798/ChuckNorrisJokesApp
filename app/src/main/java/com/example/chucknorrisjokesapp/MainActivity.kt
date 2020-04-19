package com.example.chucknorrisjokesapp

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewTreeObserver
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.json.JsonConfiguration.Companion
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity ()
{
    private val disposable = CompositeDisposable()
    private val listeOfJokes: MutableList<Joke> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recycler: RecyclerView = findViewById(R.id.RecyclerView)
        recycler.layoutManager = LinearLayoutManager(this)

        val jokeService: JokeApiService = JokeApiServiceFactory.createJokeApiService()
        val progressBar: ProgressBar = findViewById(R.id.ProgressBar)

        val joke: Single<Joke> = jokeService.giveMeAJoke()
        val adapteur = JokeAdapter(listeOfJokes){ jokeAdapter : JokeAdapter ->
            val resultSubscribe = joke.subscribeOn(Schedulers.io())
                .delay(100, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .repeat(10)
                .doOnError { println("Le Single<Joke> renvoie une erreur") }
                .doOnSubscribe  {   progressBar.visibility = VISIBLE    }
                .doOnNext { joke ->
                    println("Yaaay on peut utiliser la Jooooke : ${joke.value}")
                    jokeAdapter.setJokes(joke)

                }
                .doAfterTerminate   {   progressBar.visibility = INVISIBLE      }
                .subscribe()
            disposable.add(resultSubscribe)
        }
        recycler.adapter = adapteur


        if (savedInstanceState != null) {
            val listeJokesString = savedInstanceState.getString("listeJokesString")
            if (listeJokesString != null) {
                val json = Json(JsonConfiguration.Stable)
                val listeJokesSaved = json.parse(Joke.serializer().list, listeJokesString)
                listeJokesSaved.forEach{Joke -> adapteur.setJokes(Joke) }
            }
        } else {
            adapteur.onBottomReached(adapteur)
        }

        recycler.viewTreeObserver.addOnScrollChangedListener(ViewTreeObserver.OnScrollChangedListener {
            if (!recycler.canScrollVertically(1)) {
                adapteur.onBottomReached(adapteur)
            }
        }
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val json = Json(JsonConfiguration.Stable)
        val listeJokesString = json.stringify(Joke.serializer().list, listeOfJokes)
        outState.putString("listeJokesString", listeJokesString)
        super.onSaveInstanceState(outState)
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
