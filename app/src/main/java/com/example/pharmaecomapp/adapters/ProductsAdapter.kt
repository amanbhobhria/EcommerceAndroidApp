package com.example.pharmaecomapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmaecomapp.databinding.ProductItemBinding
import com.example.pharmaecomapp.models.Product

class ProductsAdapter(
    private val context: Context,
    private val products: List<Product>,
    private val cartButtonClickListener: CartButtonClickListener,
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
    private var btnAddToCart: Button? = null



    interface CartButtonClickListener {
        fun onAddToCartButtonClick(product: Product, quantity: Int) // Callback method
    }

    inner class ProductViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.binding.apply {
            ivProductImage.setImageResource(product.imageResId)
            tvProductName.text = product.name
            tvProductPrice.text = "$${product.productPrice}"

            // Initial setup
            btnAdd.visibility = View.VISIBLE
            llQuantity.visibility = View.GONE

            btnAdd.setOnClickListener {
                btnAdd.visibility = View.GONE
                llQuantity.visibility = View.VISIBLE
            }

            btnIncrease.setOnClickListener {
                val currentQuantity = tvQuantity.text.toString().toInt()
                tvQuantity.text = (currentQuantity + 1).toString()
            }

            btnDecrease.setOnClickListener {
                val currentQuantity = tvQuantity.text.toString().toInt()
                if (currentQuantity > 1) {
                    tvQuantity.text = (currentQuantity - 1).toString()
                }
            }

            // Handle the "Add to Cart" button click inside the adapter
            btnAddToCart?.setOnClickListener {
                val quantity = tvQuantity.text.toString().toInt()
                cartButtonClickListener.onAddToCartButtonClick(product, quantity) // Trigger callback
//                btnAdd.visibility = View.VISIBLE
//                llQuantity.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = products.size

    fun setCartButton(button: Button) {
        this.btnAddToCart = button
    }

}



