package com.example.handson_assignment

import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val edt_expense = findViewById<EditText>(R.id.id_editText_expense)
        val edt_amount = findViewById<EditText>(R.id.id_editText_amount)

        val datepicker = findViewById<DatePicker>(R.id.id_datePicker)

        val btn_add = findViewById<Button>(R.id.id_add_btn)
        val btn_filter = findViewById<Button>(R.id.id_filter_btn)
        val btn_showall = findViewById<Button>(R.id.id_showall_btn)




    }
}