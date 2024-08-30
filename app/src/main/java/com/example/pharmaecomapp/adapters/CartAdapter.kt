package com.example.pharmaecomapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmaecomapp.activity.CartActivity
import com.example.pharmaecomapp.cart.CartManager
import com.example.pharmaecomapp.databinding.CartItemBinding
import com.example.pharmaecomapp.models.Product

class CartAdapter(
    private val context: Context,
    private val cartItems: List<Pair<Product, Int>>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val (product, quantity) = cartItems[position]

        holder.binding.apply {
            tvProductName.text = product.name
            tvProductPrice.text = "$${product.productPrice}"
            tvQuantity.text = quantity.toString()

            btnRemove.setOnClickListener {
                CartManager.removeFromCart(product)
                // Refresh the list
                notifyDataSetChanged()
                // Update the total price in CartActivity
                (context as CartActivity).updateTotalPrice()
            }
        }
    }

    override fun getItemCount(): Int = cartItems.size
}
