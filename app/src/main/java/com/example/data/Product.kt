package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val price: Double,
    val description: String,
    val imageUri: String = "",
    val whatsappNumber: String = "923472065158",
    val category: String = "Garments",
    val timestamp: Long = System.currentTimeMillis()
)
