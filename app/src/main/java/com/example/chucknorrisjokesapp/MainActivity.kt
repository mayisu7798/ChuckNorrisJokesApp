package com.example.chucknorrisjokesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.*
import android.view.ViewTreeObserver
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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

        adapteur.onBottomReached(adapteur)
        recycler.viewTreeObserver.addOnScrollChangedListener ( ViewTreeObserver.OnScrollChangedListener {
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
