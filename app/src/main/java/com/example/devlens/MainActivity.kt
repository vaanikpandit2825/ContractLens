package com.example.devlens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.devlens.R
import androidx.activity.result.contract.ActivityResultContracts
import android.view.View
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private val filePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if(uri!=null){
                val inputStream  = contentResolver.openInputStream(uri)
                val text=inputStream?.bufferedReader().use{ it?.readText()}
                Toast.makeText(this,text?.take(100),Toast.LENGTH_LONG).show()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById< MaterialButton>(R.id.btnUpload)
        btn.setOnClickListener {
            filePicker.launch("*/*")
        }
    }
}