package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.Product
import com.example.ui.theme.AmazonNavy
import com.example.ui.theme.BrandOrange
import com.example.ui.theme.WhatsAppGreen

@Composable
fun CheckoutDialog(
    product: Product,
    onDismiss: () -> Unit,
    onConfirmOrder: (name: String, phone: String, address: String, paymentMethod: String) -> Unit
) {
    var customerName by remember { mutableStateOf("") }
    var customerPhone by remember { mutableStateOf("") }
    var customerAddress by remember { mutableStateOf("") }
    var selectedPayment by remember { mutableStateOf("Cash on Delivery (COD)") }
    var errorMessage by remember { mutableStateOf("") }

    val paymentOptions = listOf(
        "Cash on Delivery (COD)",
        "EasyPaisa",
        "JazzCash",
        "Bank Account Transfer"
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Checkout",
                        tint = AmazonNavy,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Checkout - Three Brothers",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AmazonNavy
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Product Summary Box
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = product.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFF222222)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Price: ${product.price.toInt()} PKR",
                            fontWeight = FontWeight.ExtraBold,
                            color = BrandOrange,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Enter Customer Details",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = AmazonNavy
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = customerName,
                    onValueChange = { customerName = it; errorMessage = "" },
                    label = { Text("Customer Name *") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("checkout_name_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AmazonNavy,
                        focusedLabelColor = AmazonNavy
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = customerPhone,
                    onValueChange = { customerPhone = it; errorMessage = "" },
                    label = { Text("Phone / WhatsApp Number *") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("checkout_phone_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AmazonNavy,
                        focusedLabelColor = AmazonNavy
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = customerAddress,
                    onValueChange = { customerAddress = it; errorMessage = "" },
                    label = { Text("Complete Delivery Address *") },
                    minLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("checkout_address_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AmazonNavy,
                        focusedLabelColor = AmazonNavy
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Select Payment Method",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = AmazonNavy
                )

                Spacer(modifier = Modifier.height(6.dp))

                paymentOptions.forEach { method ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedPayment = method }
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = (selectedPayment == method),
                            onClick = { selectedPayment = method },
                            colors = RadioButtonDefaults.colors(selectedColor = AmazonNavy)
                        )
                        Text(
                            text = method,
                            fontSize = 13.sp,
                            fontWeight = if (selectedPayment == method) FontWeight.Bold else FontWeight.Normal,
                            color = Color(0xFF333333),
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }

                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel", color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (customerName.isBlank() || customerPhone.isBlank() || customerAddress.isBlank()) {
                                errorMessage = "Please fill in all customer details!"
                            } else {
                                onConfirmOrder(customerName, customerPhone, customerAddress, selectedPayment)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WhatsAppGreen,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(2f)
                            .testTag("checkout_confirm_button")
                    ) {
                        Text("WhatsApp Checkout", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
