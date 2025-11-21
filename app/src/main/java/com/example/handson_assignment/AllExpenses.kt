package com.example.handson_assignment

import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import database.ExpenseDatabase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AllExpenses : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_expenses)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        val listView = findViewById<ListView>(R.id.allExpensesList)

        val dao = ExpenseDatabase.getInstance(this).expenseDao()

        lifecycleScope.launch {

            dao.getAllExpenses().collect { list ->

                val mapped = list.map { e ->

                    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(Date(e.date))

                    mapOf(
                        "line1" to e.name,
                        "line2" to "${e.amount}  ($date)"
                    )
                }

                val adapter = SimpleAdapter(
                    this@AllExpenses,
                    mapped,
                    android.R.layout.simple_list_item_2,
                    arrayOf("line1", "line2"),
                    intArrayOf(android.R.id.text1, android.R.id.text2)
                )

                listView.adapter = adapter
            }
        }
    }
}
