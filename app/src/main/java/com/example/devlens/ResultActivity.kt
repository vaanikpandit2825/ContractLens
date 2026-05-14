package com.example.devlens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvClauses = findViewById<TextView>(R.id.tvClauses)

        val result = intent.getStringExtra("result")

        if (!result.isNullOrBlank()) {
            tvClauses.text = result
        } else {
            tvClauses.text = "No analysis available. Please go back and try again."
        }
    }
}