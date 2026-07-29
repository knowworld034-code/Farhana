package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.EcommerceDatabase
import com.example.data.EcommerceRepository
import com.example.ui.EcommerceViewModel
import com.example.ui.EcommerceViewModelFactory
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MainAppScreen
import com.example.ui.theme.ThreeBrothersTheme

class MainActivity : ComponentActivity() {

    private val viewModel: EcommerceViewModel by viewModels {
        val database = EcommerceDatabase.getDatabase(applicationContext)
        val repository = EcommerceRepository(database.ecommerceDao())
        EcommerceViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThreeBrothersTheme {
                val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
                val userEmail by viewModel.userEmail.collectAsStateWithLifecycle()
                val activeTab by viewModel.activeTab.collectAsStateWithLifecycle()
                val products by viewModel.products.collectAsStateWithLifecycle()
                val orders by viewModel.orders.collectAsStateWithLifecycle()
                val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
                val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
                val context = LocalContext.current

                if (!isLoggedIn) {
                    LoginScreen(
                        onLoginSubmitted = { email, pass ->
                            viewModel.login(email, pass)
                        }
                    )
                } else {
                    MainAppScreen(
                        userEmail = userEmail,
                        activeTab = activeTab,
                        products = products,
                        orders = orders,
                        searchQuery = searchQuery,
                        selectedCategory = selectedCategory,
                        onTabSelected = { tab -> viewModel.setActiveTab(tab) },
                        onLogout = { viewModel.logout() },
                        onSearchChange = { query -> viewModel.setSearchQuery(query) },
                        onCategoryChange = { category -> viewModel.setCategory(category) },
                        onPublishProduct = { name, price, desc, imageUri, whatsappNum, category ->
                            viewModel.addProduct(name, price, desc, imageUri, whatsappNum, category) {}
                        },
                        onDeleteProduct = { product -> viewModel.deleteProduct(product) },
                        onPlaceOrder = { product, name, phone, address, payment ->
                            viewModel.placeOrder(product, name, phone, address, payment, context)
                        },
                        onDeleteOrder = { order -> viewModel.deleteOrder(order) }
                    )
                }
            }
        }
    }
}
