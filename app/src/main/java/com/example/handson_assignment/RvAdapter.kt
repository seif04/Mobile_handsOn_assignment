package com.example.handson_assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import database.Expense // Make sure this import points to your Expense entity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RvAdapter(private val expenseList: List<Expense>) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentExpense = expenseList[position]

        holder.textExpense.text = currentExpense.name

        // Set the amount (formatted as currency)
        holder.textAmount.text = "$${currentExpense.amount}"

        // Format the date (assuming 'date' in Expense is a Long timestamp)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateString = dateFormat.format(Date(currentExpense.date))
        holder.textDate.text = dateString
    }

    override fun getItemCount(): Int {
        // Return the size of the list
        return expenseList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize the views from your list_item layout
        // Make sure these IDs match what is in your XML file
        val textExpense: TextView = itemView.findViewById(R.id.id_editText_expense)
        val textAmount: TextView = itemView.findViewById(R.id.id_editText_amount)
        val textDate: TextView = itemView.findViewById(R.id.id_datePicker)
    }
}
