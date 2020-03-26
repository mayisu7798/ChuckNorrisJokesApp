package com.example.chucknorrisjokesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

class JokeAdapter (private var listJokes : StaticList) : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>()
{
    class JokeViewHolder (var textView : TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.joke_layout, parent, false) as TextView
        return JokeViewHolder(textView)
    }
    override fun getItemCount(): Int  {
        return listJokes.list.size
    }
    override fun onBindViewHolder(holder: JokeViewHolder, position: Int)  {
       holder.textView.text = listJokes.list[position]
    }
    fun setJokes(listJokes : StaticList)   {
        this.listJokes = listJokes
        this.notifyDataSetChanged()
    }
}