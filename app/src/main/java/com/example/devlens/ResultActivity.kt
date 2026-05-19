package com.example.devlens

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val btnBack             = findViewById<ImageButton>(R.id.btnBack)
        val tvRiskBadge         = findViewById<TextView>(R.id.tvRiskBadge)
        val tvSummary           = findViewById<TextView>(R.id.tvSummary)
        val tvRisks             = findViewById<TextView>(R.id.tvRisks)
        val tvClauses           = findViewById<TextView>(R.id.tvClauses)
        val tvRecommendations   = findViewById<TextView>(R.id.tvRecommendations)

        btnBack.setOnClickListener { finish() }

        val raw = intent.getStringExtra("result") ?: ""

        if (raw.isBlank()) {
            tvSummary.text         = "No analysis returned."
            tvRisks.text           = "-"
            tvClauses.text         = "-"
            tvRecommendations.text = "-"
            return
        }

        val sections = parseSection(raw)

        tvSummary.text         = sections["SUMMARY"]         ?: raw
        tvRisks.text           = sections["KEY RISKS"]       ?: "-"
        tvClauses.text         = sections["DANGEROUS CLAUSES"] ?: "-"
        tvRecommendations.text = sections["RECOMMENDATIONS"] ?: "-"

        val riskLevel = sections["RISK LEVEL"]?.trim()?.uppercase() ?: "UNKNOWN"
        tvRiskBadge.text = riskLevel
        tvRiskBadge.setBackgroundResource(
            when (riskLevel) {
                "HIGH"   -> R.drawable.bg_badge_red
                "MEDIUM" -> R.drawable.bg_badge_amber
                else     -> R.drawable.bg_badge_amber
            }
        )
    }

    private fun parseSection(text: String): Map<String, String> {
        val markers = listOf(
            "RISK LEVEL",
            "SUMMARY",
            "KEY RISKS",
            "DANGEROUS CLAUSES",
            "RECOMMENDATIONS"
        )
        val result = mutableMapOf<String, String>()
        val lines  = text.lines()
        var currentKey  = ""
        val currentText = StringBuilder()

        for (line in lines) {
            val trimmed = line.trim().uppercase()
            val matchedMarker = markers.firstOrNull {
                trimmed == it || trimmed == "##$it##" || trimmed.startsWith("$it:")
            }
            if (matchedMarker != null) {
                if (currentKey.isNotBlank()) {
                    result[currentKey] = currentText.toString().trim()
                }
                currentKey = matchedMarker
                currentText.clear()
                val colonIndex = line.indexOf(':')
                if (colonIndex != -1 && colonIndex < line.length - 1) {
                    currentText.append(line.substring(colonIndex + 1).trim())
                }
            } else if (currentKey.isNotBlank()) {
                currentText.appendLine(line)
            }
        }
        if (currentKey.isNotBlank()) {
            result[currentKey] = currentText.toString().trim()
        }
        return result
    }
}