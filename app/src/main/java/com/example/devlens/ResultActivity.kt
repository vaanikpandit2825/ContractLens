package com.example.devlens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    companion object {

        var pendingResult: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvClauses = findViewById<TextView>(R.id.tvClauses)

        if (pendingResult.isNotBlank()) {
            tvClauses.text = pendingResult
            pendingResult = ""
        } else {
            tvClauses.text = "No analysis available. Please go back and try again."
        }
    }
}