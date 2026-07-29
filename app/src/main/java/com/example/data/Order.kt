package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Long = 0,
    val productName: String,
    val price: Double,
    val customerName: String,
    val customerPhone: String,
    val customerAddress: String,
    val paymentMethod: String,
    val whatsappNumber: String = "923472065158",
    val timestamp: Long = System.currentTimeMillis()
)
