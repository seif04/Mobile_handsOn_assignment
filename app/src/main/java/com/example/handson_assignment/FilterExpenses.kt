package com.example.handson_assignment

import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import database.ExpenseDatabase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FilterExpenses : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_expenses)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        val listView = findViewById<ListView>(R.id.filteredList)
        val dao = ExpenseDatabase.getInstance(this).expenseDao()

        val selectedDateStart = intent.getLongExtra("selectedDate", -1L)

        if (selectedDateStart != -1L) {
            val selectedDateEnd = selectedDateStart + (24 * 60 * 60 * 1000) - 1

            lifecycleScope.launch {

                dao.getExpensesByDate(selectedDateStart, selectedDateEnd).collect { list ->

                    if (list.isEmpty()) {
                        Toast.makeText(this@FilterExpenses, "No expenses found for this date", Toast.LENGTH_SHORT).show()
                    }

                    val mapped = list.map { e ->
                        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            .format(Date(e.date))

                        mapOf(
                            "line1" to e.name,
                            "line2" to "$${e.amount}   ($formattedDate)"
                        )
                    }

                    val adapter = SimpleAdapter(
                        this@FilterExpenses,
                        mapped,
                        android.R.layout.simple_list_item_2,
                        arrayOf("line1", "line2"),
                        intArrayOf(android.R.id.text1, android.R.id.text2)
                    )

                    listView.adapter = adapter
                }
            }
        } else {
            Toast.makeText(this, "Invalid date selected", Toast.LENGTH_SHORT).show()
        }
    }
}
