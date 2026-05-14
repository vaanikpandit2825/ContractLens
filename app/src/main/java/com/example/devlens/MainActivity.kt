package com.example.devlens

import android.content.Intent
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

class MainActivity : AppCompatActivity() {

    private val client = OkHttpClient()

    private val filePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val inputStream = contentResolver.openInputStream(uri)
                            ?: throw Exception("Cannot open file")
                        val pdf = PDDocument.load(inputStream)
                        val text = PDFTextStripper().getText(pdf)
                        pdf.close()
                        inputStream.close()
                        withContext(Dispatchers.Main) {
                            analyzeContract(text)
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "PDF Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

    private fun analyzeContract(text: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val prompt = """
                    You are an expert contract lawyer.
                    Analyze this contract and give:
                    - Major risks
                    - Dangerous clauses
                    - Simple summary
                    
                    Contract:
                    ${text.take(4000)}
                """.trimIndent()

                val requestBody = JSONObject().apply {
                    put("contents", JSONArray().apply {
                        put(JSONObject().apply {
                            put("parts", JSONArray().apply {
                                put(JSONObject().apply {
                                    put("text", prompt)
                                })
                            })
                        })
                    })
                }.toString()

                val request = Request.Builder()
                    .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=...")
                    .post(requestBody.toRequestBody("application/json".toMediaType()))
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string() ?: throw Exception("Empty response")

                if (!response.isSuccessful) {
                    throw Exception("API Error ${response.code}: $responseBody")
                }

                val result = JSONObject(responseBody)
                    .getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text")

                withContext(Dispatchers.Main) {
                    val intent = Intent(this@MainActivity, ResultActivity::class.java)
                    intent.putExtra("result", result)
                    startActivity(intent)
                }

            } catch (e: Exception) {
                Log.e("GEMINI_FULL_ERROR", Log.getStackTraceString(e))
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PDFBoxResourceLoader.init(applicationContext)
        findViewById<MaterialButton>(R.id.btnUpload).setOnClickListener {
            filePicker.launch("application/pdf")
        }
    }
}
