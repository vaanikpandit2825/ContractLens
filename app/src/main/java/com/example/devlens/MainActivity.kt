package com.example.devlens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var btnUpload: MaterialButton

    private val filePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { extractAndAnalyze(it) }
    }

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PDFBoxResourceLoader.init(applicationContext)

        btnUpload = findViewById(R.id.btnUpload)
        btnUpload.setOnClickListener {
            filePickerLauncher.launch("application/pdf")
        }
    }

    private fun extractAndAnalyze(uri: Uri) {
        lifecycleScope.launch {
            try {
                setLoading(true, "Extracting PDF...")

                val pdfText = withContext(Dispatchers.IO) {
                    extractTextFromPdf(uri)
                }

                if (pdfText.isBlank()) {
                    setLoading(false)
                    Toast.makeText(this@MainActivity,
                        "Could not extract text. Is this a scanned PDF?",
                        Toast.LENGTH_LONG).show()
                    return@launch
                }

                setLoading(true, "Analyzing with AI...")

                val result = withContext(Dispatchers.IO) {
                    analyzeWithGemini(pdfText)
                }

                setLoading(false)

                startActivity(
                    Intent(this@MainActivity, ResultActivity::class.java)
                        .putExtra("result", result)
                )

            } catch (e: Exception) {
                setLoading(false)
                Log.e("DEVLENS_ERROR", Log.getStackTraceString(e))
                Toast.makeText(this@MainActivity,
                    "Error: ${e.message?.take(200)}",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun extractTextFromPdf(uri: Uri): String {
        val stream = contentResolver.openInputStream(uri)
            ?: throw Exception("Cannot open file")
        return stream.use {
            PDDocument.load(it).use { doc ->
                PDFTextStripper().getText(doc)
            }
        }
    }

    private fun analyzeWithGemini(contractText: String): String {
        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=${BuildConfig.GEMINI_API_KEY}"

        val prompt = """
            You are a legal contract analysis expert. Analyze the contract and respond using EXACTLY these section headers in this order. Do not add extra headers or change the wording.

            RISK LEVEL: [write only one word: HIGH, MEDIUM, or LOW]

            SUMMARY
            [2-3 sentences describing what this contract is and its purpose]

            KEY RISKS
            [bullet points starting with - describing the top risks for the signing party]

            DANGEROUS CLAUSES
            [for each dangerous clause: quote the relevant text in quotes, then explain why it is harmful]

            RECOMMENDATIONS
            [bullet points starting with - of specific things the signing party should negotiate or watch out for]

            Contract:
            ${contractText.take(25000)}
        """.trimIndent()

        val body = JSONObject().apply {
            put("contents", JSONArray().apply {
                put(JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().put("text", prompt))
                    })
                })
            })
        }.toString()

        val response = httpClient.newCall(
            Request.Builder()
                .url(url)
                .post(body.toRequestBody("application/json".toMediaType()))
                .build()
        ).execute()

        val responseBody = response.body?.string()
            ?: throw Exception("Empty response from Gemini")

        if (!response.isSuccessful) {
            throw Exception("API Error ${response.code}: $responseBody")
        }

        return JSONObject(responseBody)
            .getJSONArray("candidates")
            .getJSONObject(0)
            .getJSONObject("content")
            .getJSONArray("parts")
            .getJSONObject(0)
            .getString("text")
    }

    private fun setLoading(loading: Boolean, label: String = "Upload contract") {
        btnUpload.isEnabled = !loading
        btnUpload.text = if (loading) label else "Upload contract"
    }
}