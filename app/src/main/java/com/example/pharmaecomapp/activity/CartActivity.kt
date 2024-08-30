package com.example.pharmaecomapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmaecomapp.R
import com.example.pharmaecomapp.adapters.CartAdapter
import com.example.pharmaecomapp.cart.CartManager
import com.example.pharmaecomapp.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding


    private lateinit var cartAdapter: CartAdapter  // Create this adapter to show cart items

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.app_color)



        setupRecyclerView()
        updateTotalPrice()
        backBtnFun()
        checkOut()



    }

    private fun checkOut() {
        binding.btnCheckout.setOnClickListener {
            // Handle checkout logic here
            // For example, navigate to a payment activity or place an order
            CartManager.clearCart() // Clear cart after order is placed
            finish()
        }
    }

    private fun backBtnFun() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(this, CartManager.getCartItems().toList())
        binding.rvCartItems.adapter = cartAdapter
        binding.rvCartItems.layoutManager = LinearLayoutManager(this)
    }

    fun updateTotalPrice() {
        val totalPrice = CartManager.calculateTotalPrice()
        binding.tvTotalPrice.text = "Total: $${String.format("%.2f", totalPrice)}"
    }
}
