package com.example.devlens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import com.google.ai.client.generativeai.GenerativeModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private val filePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                val inputStream = contentResolver.openInputStream(uri)
                if (inputStream != null) {
                    try {
                        val pdf = PDDocument.load(inputStream)
                        val stripper = PDFTextStripper()
                        val text = stripper.getText(pdf)
                        pdf.close()
                        analyzeContract(text)
                    } catch (e: Exception) {
                        Toast.makeText(this, "Failed to read PDF: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "Could not open file.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun analyzeContract(text: String) {

        // Safety check — avoid sending an empty document
        if (text.isBlank()) {
            Toast.makeText(this, "PDF appears to be empty or unreadable.", Toast.LENGTH_SHORT).show()
            return
        }

        val model = GenerativeModel(
            modelName = "gemini-1.5-flash",   // ✅ Fixed: removed "-latest"
            apiKey = BuildConfig.GEMINI_API_KEY
        )

        val prompt = """
            You are an expert contract lawyer, legal risk analyst, and compliance advisor.

            Analyze the following contract in extreme depth.
            Finally, give a SIMPLE SUMMARY in plain English for a non-legal person.

            Contract:
            $text
        """.trimIndent()

        lifecycleScope.launch {
            try {
                val response = model.generateContent(prompt)
                val result = response.text ?: "No response received from Gemini."


                ResultActivity.pendingResult = result
                val intent = Intent(this@MainActivity, ResultActivity::class.java)
                startActivity(intent)

            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PDFBoxResourceLoader.init(applicationContext)

        val btn = findViewById<MaterialButton>(R.id.btnUpload)
        btn.setOnClickListener {
            filePicker.launch("application/pdf")
        }
    }
}