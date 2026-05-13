package com.example.devlens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.android.material.button.MaterialButton
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import kotlinx.coroutines.launch

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
                        inputStream.close()

                        analyzeContract(text)

                    } catch (e: Exception) {

                        Toast.makeText(
                            this,
                            "PDF Error: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    private fun analyzeContract(text: String) {

        val model = GenerativeModel(
            modelName = "gemini-1.5-flash-latest",
            apiKey = BuildConfig.GEMINI_API_KEY
        )

        lifecycleScope.launch {

            try {

                val response = model.generateContent(
                    """
                    You are an expert contract lawyer.

                    Analyze this contract and give:
                    - Major risks
                    - Dangerous clauses
                    - Simple summary

                    Contract:
                    ${'$'}{text.take(4000)}
                    """.trimIndent()
                )

                val result = response.text ?: "No response from Gemini"

                val intent = Intent(
                    this@MainActivity,
                    ResultActivity::class.java
                )

                intent.putExtra("result", result)

                startActivity(intent)

            } catch (e: Exception) {

                e.printStackTrace()

                Log.e("GEMINI_FULL_ERROR", Log.getStackTraceString(e))

                Toast.makeText(
                    this@MainActivity,
                    Log.getStackTraceString(e).take(300),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        Toast.makeText(this, BuildConfig.GEMINI_API_KEY, Toast.LENGTH_LONG).show()

        Log.d("API_TEST", BuildConfig.GEMINI_API_KEY)

        PDFBoxResourceLoader.init(applicationContext)

        Log.d("API_TEST", BuildConfig.GEMINI_API_KEY)

        val btn = findViewById<MaterialButton>(R.id.btnUpload)

        btn.setOnClickListener {

            filePicker.launch("application/pdf")
        }
    }
}