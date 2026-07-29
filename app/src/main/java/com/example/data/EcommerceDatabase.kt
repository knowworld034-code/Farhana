package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Product::class, Order::class],
    version = 1,
    exportSchema = false
)
abstract class EcommerceDatabase : RoomDatabase() {
    abstract fun ecommerceDao(): EcommerceDao

    companion object {
        @Volatile
        private var INSTANCE: EcommerceDatabase? = null

        fun getDatabase(context: Context): EcommerceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EcommerceDatabase::class.java,
                    "three_brothers_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
