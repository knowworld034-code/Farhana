package com.example.data

import kotlinx.coroutines.flow.Flow

class EcommerceRepository(private val dao: EcommerceDao) {

    val products: Flow<List<Product>> = dao.getAllProducts()
    val orders: Flow<List<Order>> = dao.getAllOrders()

    suspend fun insertProduct(product: Product): Long {
        return dao.insertProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        dao.deleteProduct(product)
    }

    suspend fun insertOrder(order: Order): Long {
        return dao.insertOrder(order)
    }

    suspend fun deleteOrder(order: Order) {
        dao.deleteOrder(order)
    }

    suspend fun seedDefaultProductsIfEmpty() {
        val count = dao.getProductCount()
        if (count == 0) {
            val sampleProducts = listOf(
                Product(
                    name = "Three Brothers Designer Kurta",
                    price = 3500.0,
                    description = "Premium embroidered navy blue cotton gents kurta suit with classic ban collar.",
                    imageUri = "img_product_kurta",
                    whatsappNumber = "923472065158",
                    category = "Gents Kurta"
                ),
                Product(
                    name = "Unstitched Lawn 3-Piece Suit",
                    price = 4200.0,
                    description = "Luxury printed summer lawn collection with dyed trousers and chiffon dupatta.",
                    imageUri = "img_hero_banner",
                    whatsappNumber = "923472065158",
                    category = "Lawn Collection"
                ),
                Product(
                    name = "Casual Fine Cotton Shirt",
                    price = 1850.0,
                    description = "Breathable 100% pure cotton formal & casual shirt with comfortable fit.",
                    imageUri = "img_app_icon",
                    whatsappNumber = "923472065158",
                    category = "Shirts"
                ),
                Product(
                    name = "Gents Winter Fleece Hoodie",
                    price = 2800.0,
                    description = "Soft fleece warm pullover hoodie for winter garments collection.",
                    imageUri = "img_hero_banner",
                    whatsappNumber = "923472065158",
                    category = "Winter Wear"
                )
            )
            for (product in sampleProducts) {
                dao.insertProduct(product)
            }
        }
    }
}
