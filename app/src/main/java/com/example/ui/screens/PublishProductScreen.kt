package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ProductImage
import com.example.ui.theme.AmazonNavy
import com.example.ui.theme.BrandOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishProductScreen(
    onPublishProduct: (
        name: String,
        price: Double,
        desc: String,
        imageUri: String,
        whatsappNum: String,
        category: String
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    var productName by remember { mutableStateOf("") }
    var priceStr by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var whatsappNum by remember { mutableStateOf("923472065158") }
    var imageUri by remember { mutableStateOf("img_product_kurta") }
    var category by remember { mutableStateOf("Gents Kurta") }
    var expandedCategory by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    val categoryOptions = listOf("Gents Kurta", "Lawn Collection", "Shirts", "Winter Wear", "Unstitched Fabric")

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri.toString()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFDDDDDD), RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CloudUpload,
                        contentDescription = "Upload",
                        tint = AmazonNavy,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Upload New Product (Admin Panel)",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it; errorMessage = ""; successMessage = "" },
                    label = { Text("Product Name:") },
                    placeholder = { Text("Enter product name (e.g., Designer Kurta)") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("publish_product_name_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrandOrange,
                        focusedLabelColor = BrandOrange
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = priceStr,
                    onValueChange = { priceStr = it; errorMessage = ""; successMessage = "" },
                    label = { Text("Price (in PKR):") },
                    placeholder = { Text("Enter price (e.g., 3500)") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("publish_product_price_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrandOrange,
                        focusedLabelColor = BrandOrange
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Category Selector
                ExposedDropdownMenuBox(
                    expanded = expandedCategory,
                    onExpandedChange = { expandedCategory = !expandedCategory },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category:") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategory) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrandOrange,
                            focusedLabelColor = BrandOrange
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expandedCategory,
                        onDismissRequest = { expandedCategory = false }
                    ) {
                        categoryOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    category = option
                                    expandedCategory = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it; errorMessage = ""; successMessage = "" },
                    label = { Text("Description:") },
                    placeholder = { Text("Enter product details, fabric, sizes, etc.") },
                    minLines = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("publish_product_desc_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrandOrange,
                        focusedLabelColor = BrandOrange
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Select Image Asset:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Image Preview Card & Selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFEEEEEE))
                            .border(1.dp, Color(0xFFCCCCCC), RoundedCornerShape(8.dp))
                    ) {
                        ProductImage(
                            imageUri = imageUri,
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        OutlinedButton(
                            onClick = { photoPickerLauncher.launch("image/*") },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.testTag("select_gallery_image_btn")
                        ) {
                            Icon(imageVector = Icons.Default.AddPhotoAlternate, contentDescription = "Pick Image", modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Pick Image File", fontSize = 12.sp)
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Quick preset buttons
                        Row {
                            Surface(
                                color = if (imageUri == "img_product_kurta") AmazonNavy else Color(0xFFE0E0E0),
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier
                                    .clickable { imageUri = "img_product_kurta" }
                                    .padding(end = 4.dp)
                            ) {
                                Text(
                                    text = "Kurta",
                                    fontSize = 11.sp,
                                    color = if (imageUri == "img_product_kurta") Color.White else Color.Black,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }

                            Surface(
                                color = if (imageUri == "img_hero_banner") AmazonNavy else Color(0xFFE0E0E0),
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier
                                    .clickable { imageUri = "img_hero_banner" }
                                    .padding(end = 4.dp)
                            ) {
                                Text(
                                    text = "Suit Banner",
                                    fontSize = 11.sp,
                                    color = if (imageUri == "img_hero_banner") Color.White else Color.Black,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = whatsappNum,
                    onValueChange = { whatsappNum = it },
                    label = { Text("Target WhatsApp Number (Formatted):") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("publish_product_whatsapp_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrandOrange,
                        focusedLabelColor = BrandOrange
                    )
                )

                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }

                if (successMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Success", tint = Color(0xFF25D366))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = successMessage,
                            color = Color(0xFF25D366),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val priceVal = priceStr.toDoubleOrNull()
                        if (productName.isBlank()) {
                            errorMessage = "Please enter product name!"
                        } else if (priceVal == null || priceVal <= 0) {
                            errorMessage = "Please enter a valid price in PKR!"
                        } else {
                            onPublishProduct(
                                productName,
                                priceVal,
                                description,
                                imageUri,
                                whatsappNum,
                                category
                            )
                            successMessage = "Product '$productName' published successfully to Shop!"
                            productName = ""
                            priceStr = ""
                            description = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrandOrange,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("publish_product_submit_btn")
                ) {
                    Text(
                        text = "Publish Product",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
