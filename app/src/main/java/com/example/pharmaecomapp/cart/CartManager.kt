package com.example.pharmaecomapp.cart

import android.content.Context
import com.example.pharmaecomapp.models.Product

object CartManager {
    private val cartItems = mutableMapOf<Product, Int>()

    // Add product to cart
    fun addToCart(product: Product, quantity: Int) {
        if (quantity > 0) {
            cartItems[product] = quantity
        } else {
            cartItems.remove(product)
        }
    }

    // Remove product from cart
    fun removeFromCart(product: Product) {
        cartItems.remove(product)
    }

    // Update quantity of a product in the cart
    fun updateQuantity(product: Product, quantity: Int) {
        if (quantity > 0) {
            cartItems[product] = quantity
        } else {
            cartItems.remove(product)
        }
    }

    // Get all cart items
    fun getCartItems(): Map<Product, Int> {
        return cartItems
    }

    // Clear the cart after order placement
    fun clearCart() {
        cartItems.clear()
    }

    // Calculate total price of the cart
    fun calculateTotalPrice(): Double {
        var totalPrice = 0.0
        cartItems.forEach { (product, quantity) ->
            totalPrice += product.productPrice * quantity
        }
        return totalPrice
    }

    // Save cart to SharedPreferences (or any other persistent storage)
    fun saveCartToStorage(context: Context) {
        // Implement save logic here
    }

    // Load cart from SharedPreferences (or any other persistent storage)
    fun loadCartFromStorage(context: Context) {
        // Implement load logic here
    }
}
