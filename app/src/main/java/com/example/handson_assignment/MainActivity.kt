package com.example.handson_assignment

import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import database.Expense
import database.ExpenseDatabase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lateinit var dao: database.ExpenseDao
        val db = ExpenseDatabase.getInstance(applicationContext)
        dao = db.expenseDao()
        var id = 0

        val edt_expense = findViewById<EditText>(R.id.id_editText_expense)
        val edt_amount = findViewById<EditText>(R.id.id_editText_amount)

        val datepicker = findViewById<DatePicker>(R.id.id_datePicker)

        val btn_add = findViewById<Button>(R.id.id_add_btn)
        val btn_filter = findViewById<Button>(R.id.id_filter_btn)
        val btn_showall = findViewById<Button>(R.id.id_showall_btn)

        btn_add.setOnClickListener {
            val expense = edt_expense.text.toString().trim()
            val amount = edt_amount.text.toString().trim().toDouble()
            val date = LocalDate.of(datepicker.year, datepicker.month + 1, datepicker.dayOfMonth)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

            lifecycleScope.launch {
                dao.addExpense(Expense(id++, expense, amount, date))
            }

            makeText(this, "Expense Added", LENGTH_SHORT).show()
        }

    }
}