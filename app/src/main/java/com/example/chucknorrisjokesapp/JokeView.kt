package com.example.chucknorrisjokesapp

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class JokeView @JvmOverloads constructor(pContext : Context)
    : ConstraintLayout(pContext) {

    init {  inflate(pContext, R.layout.joke_layout, this)   }

    data class Model(val textView: TextView, val buttonShare : ImageButton, val buttonFavori : ImageButton)

    fun setupView(model: Model)
    {
        var textView : TextView = findViewById(R.id.TextViewJoke)
        textView = model.textView

        var buttonShare : ImageButton = findViewById(R.id.ButtonPartager)
        buttonShare = model.buttonShare

        var buttonFavori : ImageButton = findViewById(R.id.ButtonFavori)
        buttonFavori = model.buttonFavori
    }
}