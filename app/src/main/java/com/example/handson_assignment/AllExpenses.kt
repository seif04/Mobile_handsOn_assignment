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

        // da el code el mas2ool en el layout mayet2asarsh bel status bar aw el navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        // da el ListView el hayzhar feh kol el expenses
        val listView = findViewById<ListView>(R.id.allExpensesList)

        // gebna el DAO 3ashan ne2dar ne3mel get ll data mn el database
        val dao = ExpenseDatabase.getInstance(this).expenseDao()

        // 3ashan el DAO بيرجع Flow mesh LiveData, lazm ne-collecto gwa lifecycleScope
        lifecycleScope.launch {

            // collect ya3ny kol lama el data tet8ayar, el code da hyetnada tani wahdo
            dao.getAllExpenses().collect { list ->

                // hnro7 ne3mel map ll data 3ashan el SimpleAdapter y2dar ye2raha
                val mapped = list.map { e ->

                    // almawl el tareekh mn Long timestamp le format readable zay yyyy-MM-dd
                    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(Date(e.date))

                    // line1 hyb2a esm el expense
                    // line2 hyb2a el amount wel tareekh
                    mapOf(
                        "line1" to e.name,
                        "line2" to "${e.amount}  ($date)"
                    )
                }

                // da adapter sadee2 by3rad 2 lines fe kol row
                val adapter = SimpleAdapter(
                    this@AllExpenses,
                    mapped,
                    android.R.layout.simple_list_item_2,
                    arrayOf("line1", "line2"),
                    intArrayOf(android.R.id.text1, android.R.id.text2)
                )

                // hena bawadda3 el adapter fe el ListView
                listView.adapter = adapter
            }
        }
    }
}
