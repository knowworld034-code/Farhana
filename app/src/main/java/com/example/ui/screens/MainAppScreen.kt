package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Order
import com.example.data.Product
import com.example.ui.MainTab
import com.example.ui.components.CheckoutDialog
import com.example.ui.components.HeaderAndOwnerSection
import com.example.ui.theme.AmazonNavy
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandOrange
import com.example.ui.theme.LightBackground

@Composable
fun MainAppScreen(
    userEmail: String,
    activeTab: MainTab,
    products: List<Product>,
    orders: List<Order>,
    searchQuery: String,
    selectedCategory: String,
    onTabSelected: (MainTab) -> Unit,
    onLogout: () -> Unit,
    onSearchChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onPublishProduct: (name: String, price: Double, desc: String, imageUri: String, whatsappNum: String, category: String) -> Unit,
    onDeleteProduct: (Product) -> Unit,
    onPlaceOrder: (product: Product, name: String, phone: String, address: String, payment: String) -> Unit,
    onDeleteOrder: (Order) -> Unit,
    modifier: Modifier = Modifier
) {
    var checkoutProduct by remember { mutableStateOf<Product?>(null) }

    Scaffold(
        topBar = {
            HeaderAndOwnerSection(
                userEmail = userEmail,
                onLogout = onLogout
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = AmazonNavy,
                contentColor = Color.White
            ) {
                NavigationBarItem(
                    selected = (activeTab == MainTab.SHOP),
                    onClick = { onTabSelected(MainTab.SHOP) },
                    icon = { Icon(imageVector = Icons.Default.Storefront, contentDescription = "Shop") },
                    label = { Text("Shop", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AmazonNavy,
                        selectedTextColor = BrandGold,
                        indicatorColor = BrandGold,
                        unselectedIconColor = Color.LightGray,
                        unselectedTextColor = Color.LightGray
                    ),
                    modifier = Modifier.testTag("tab_shop")
                )

                NavigationBarItem(
                    selected = (activeTab == MainTab.PUBLISH),
                    onClick = { onTabSelected(MainTab.PUBLISH) },
                    icon = { Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Publish") },
                    label = { Text("Publish", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AmazonNavy,
                        selectedTextColor = BrandGold,
                        indicatorColor = BrandGold,
                        unselectedIconColor = Color.LightGray,
                        unselectedTextColor = Color.LightGray
                    ),
                    modifier = Modifier.testTag("tab_publish")
                )

                NavigationBarItem(
                    selected = (activeTab == MainTab.ORDERS),
                    onClick = { onTabSelected(MainTab.ORDERS) },
                    icon = { Icon(imageVector = Icons.Default.ListAlt, contentDescription = "Orders") },
                    label = { Text("Orders (${orders.size})", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AmazonNavy,
                        selectedTextColor = BrandGold,
                        indicatorColor = BrandGold,
                        unselectedIconColor = Color.LightGray,
                        unselectedTextColor = Color.LightGray
                    ),
                    modifier = Modifier.testTag("tab_orders")
                )

                NavigationBarItem(
                    selected = (activeTab == MainTab.SOCIAL),
                    onClick = { onTabSelected(MainTab.SOCIAL) },
                    icon = { Icon(imageVector = Icons.Default.Share, contentDescription = "Social") },
                    label = { Text("Links", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AmazonNavy,
                        selectedTextColor = BrandGold,
                        indicatorColor = BrandGold,
                        unselectedIconColor = Color.LightGray,
                        unselectedTextColor = Color.LightGray
                    ),
                    modifier = Modifier.testTag("tab_social")
                )
            }
        },
        containerColor = LightBackground,
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (activeTab) {
                MainTab.SHOP -> ShopScreen(
                    products = products,
                    searchQuery = searchQuery,
                    selectedCategory = selectedCategory,
                    onSearchChange = onSearchChange,
                    onCategoryChange = onCategoryChange,
                    onCheckoutClick = { product -> checkoutProduct = product },
                    onDeleteProduct = onDeleteProduct
                )

                MainTab.PUBLISH -> PublishProductScreen(
                    onPublishProduct = { name, price, desc, imageUri, whatsappNum, category ->
                        onPublishProduct(name, price, desc, imageUri, whatsappNum, category)
                    }
                )

                MainTab.ORDERS -> OrdersScreen(
                    orders = orders,
                    onDeleteOrder = onDeleteOrder
                )

                MainTab.SOCIAL -> SocialMarketingScreen()
            }

            // Checkout Dialog if a product is selected
            checkoutProduct?.let { product ->
                CheckoutDialog(
                    product = product,
                    onDismiss = { checkoutProduct = null },
                    onConfirmOrder = { name, phone, address, payment ->
                        onPlaceOrder(product, name, phone, address, payment)
                        checkoutProduct = null
                    }
                )
            }
        }
    }
}
