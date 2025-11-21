package database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Expense::class], version = 1, exportSchema = false)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    companion object {
        private var INSTANCE: ExpenseDatabase? = null

        fun getInstance(context: Context): ExpenseDatabase {
            return INSTANCE ?: synchronized(lock = this) {
                val instnaces = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                     "expense_database"
                ).build()
                INSTANCE = instnaces
                return instnaces
            }
        }

    }
}