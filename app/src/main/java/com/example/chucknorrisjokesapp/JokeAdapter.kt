package com.example.chucknorrisjokesapp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

class JokeAdapter(private var listJokes: MutableList<Joke>, val onBottomReached: (JokeAdapter) -> Unit)
    : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>()
{
    class JokeViewHolder (var jokeView : JokeView) : RecyclerView.ViewHolder(jokeView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val jokeView = JokeView(parent.context)
        jokeView.layoutParams = RecyclerView.LayoutParams(  ViewGroup.LayoutParams.MATCH_PARENT,
                                                            ViewGroup.LayoutParams.WRAP_CONTENT)
        return JokeViewHolder(jokeView)
    }
    override fun getItemCount(): Int  {
        return listJokes.size
    }
    override fun onBindViewHolder(holder: JokeViewHolder, position: Int)  {
        var textView : TextView = holder.jokeView.findViewById(R.id.TextViewJoke)
        textView.text = listJokes[position].value

        var buttonShare : ImageButton = holder.jokeView.findViewById(R.id.ButtonPartager)
        buttonShare.setOnClickListener { Log.d("Share-IdTextView", "IdTextView = " + textView.id) }

        var nombreClic : Int = 0
        var buttonFavori : ImageButton = holder.jokeView.findViewById(R.id.ButtonFavori)
        buttonFavori.setOnClickListener {
            Log.d("Favori-IdTextView", "IdTextView = " + textView.id)
            nombreClic += 1
            if (nombreClic%2 == 1) buttonFavori.setImageResource(R.drawable.star_pleine)
            else buttonFavori.setImageResource(R.drawable.star_vide)
        }

        var model : JokeView.Model = JokeView.Model(textView = textView,
                                                    buttonShare = buttonShare,
                                                    buttonFavori = buttonFavori)
        holder.jokeView.setupView(model)

    }

    fun setJokes(Joke : Joke)   {
        this.listJokes.add(Joke)
        this.notifyDataSetChanged()
    }

}



