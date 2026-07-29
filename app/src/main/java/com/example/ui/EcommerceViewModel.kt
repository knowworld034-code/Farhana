package com.example.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.Order
import com.example.data.Product
import com.example.data.EcommerceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.URLEncoder

enum class MainTab(val title: String) {
    SHOP("Shop Catalog"),
    PUBLISH("Publish Product"),
    ORDERS("Saved Orders"),
    SOCIAL("Marketing & Contact")
}

class EcommerceViewModel(private val repository: EcommerceRepository) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _userEmail = MutableStateFlow("")
    val userEmail: StateFlow<String> = _userEmail.asStateFlow()

    private val _activeTab = MutableStateFlow(MainTab.SHOP)
    val activeTab: StateFlow<MainTab> = _activeTab.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedDefaultProductsIfEmpty()
        }
    }

    val products: StateFlow<List<Product>> = combine(
        repository.products,
        _searchQuery,
        _selectedCategory
    ) { productList, query, category ->
        productList.filter { product ->
            val matchesQuery = query.isEmpty() ||
                    product.name.contains(query, ignoreCase = true) ||
                    product.description.contains(query, ignoreCase = true)
            val matchesCategory = category == "All" ||
                    product.category.equals(category, ignoreCase = true)
            matchesQuery && matchesCategory
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val orders: StateFlow<List<Order>> = repository.orders.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun login(email: String, pass: String): Boolean {
        if (email.isNotBlank() && pass.isNotBlank()) {
            _userEmail.value = email
            _isLoggedIn.value = true
            return true
        }
        return false
    }

    fun logout() {
        _isLoggedIn.value = false
        _userEmail.value = ""
    }

    fun setActiveTab(tab: MainTab) {
        _activeTab.value = tab
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun addProduct(
        name: String,
        price: Double,
        description: String,
        imageUri: String,
        whatsappNumber: String,
        category: String = "Garments",
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val validWhatsapp = if (whatsappNumber.isBlank()) "923472065158" else whatsappNumber.replace("+", "").replace(" ", "")
            val product = Product(
                name = name,
                price = price,
                description = description,
                imageUri = imageUri,
                whatsappNumber = validWhatsapp,
                category = category
            )
            repository.insertProduct(product)
            _activeTab.value = MainTab.SHOP
            onSuccess()
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }

    fun placeOrder(
        product: Product,
        customerName: String,
        customerPhone: String,
        customerAddress: String,
        paymentMethod: String,
        context: Context
    ) {
        viewModelScope.launch {
            val validWhatsapp = if (product.whatsappNumber.isBlank()) "923472065158" else product.whatsappNumber.replace("+", "").replace(" ", "")
            val order = Order(
                productName = product.name,
                price = product.price,
                customerName = customerName,
                customerPhone = customerPhone,
                customerAddress = customerAddress,
                paymentMethod = paymentMethod,
                whatsappNumber = validWhatsapp
            )
            repository.insertOrder(order)

            // Format WhatsApp checkout message exactly matching the prompt spec
            val messageText = "New Order!\n" +
                    "- Product: ${product.name}\n" +
                    "- Price: ${product.price.toInt()} PKR\n" +
                    "- Payment Method: $paymentMethod\n" +
                    "- Name: $customerName\n" +
                    "- Phone: $customerPhone\n" +
                    "- Address: $customerAddress"

            val encodedMessage = try {
                URLEncoder.encode(messageText, "UTF-8")
            } catch (e: Exception) {
                messageText
            }

            val whatsappUrl = "https://wa.me/$validWhatsapp?text=$encodedMessage"
            
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(whatsappUrl))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "Could not launch WhatsApp. Order saved locally!", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun deleteOrder(order: Order) {
        viewModelScope.launch {
            repository.deleteOrder(order)
        }
    }
}

class EcommerceViewModelFactory(private val repository: EcommerceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EcommerceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EcommerceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
