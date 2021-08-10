package com.example.noonsokmal

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.signup.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        //loss_degree Spinner
        val degreeSpinner : Spinner = findViewById(R.id.loss_degree)
        val degreeAdapter = ArrayAdapter.createFromResource(this, R.array.loss_degree, android.R.layout.simple_spinner_item)
        degreeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        degreeSpinner.adapter = degreeAdapter

        date.maxDate = System.currentTimeMillis()

    }
}