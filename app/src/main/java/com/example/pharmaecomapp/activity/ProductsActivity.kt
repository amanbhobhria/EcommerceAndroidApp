package com.example.pharmaecomapp.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pharmaecomapp.R
import com.example.pharmaecomapp.adapters.ProductsAdapter
import com.example.pharmaecomapp.databinding.ActivityProductsBinding
import com.example.pharmaecomapp.models.Product
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProductsActivity : AppCompatActivity(), ProductsAdapter.CartButtonClickListener {

    private lateinit var binding: ActivityProductsBinding
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var btnAddToCart: Button
    // Initialize the list of selected products
    private val selectedProducts = mutableMapOf<Product, Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Set up the toolbar
        setSupportActionBar(binding.toolbar)

        // Get category ID and name from the intent
        val categoryId = intent.getIntExtra("CATEGORY_ID", -1)
        val categoryName = intent.getStringExtra("CATEGORY_NAME")

        // Update the toolbar title
        supportActionBar?.title = categoryName


        Toast.makeText(this,categoryName,Toast.LENGTH_SHORT).show()

        // Sample products for the demo
        val sampleProducts = listOf(
            Product(1, "Product 1", R.drawable.eye_prod1, 100),
            Product(1, "Product 2", R.drawable.eye_prod1, 130),
            Product(1, "Product 3", R.drawable.eye_prod1, 120),
            Product(1, "Product 4", R.drawable.eye_prod1, 122),
            Product(2, "Product 1", R.drawable.eye_prod1, 199),
            Product(2, "Product 2", R.drawable.throat_img, 100),
            Product(2, "Product 3", R.drawable.throat_img, 150),
            Product(3, "Product 3", R.drawable.throat_img, 180),
            Product(4, "Product 4", R.drawable.throat_img, 200),
            Product(4, "Product 5", R.drawable.throat_img, 250)
        )

        // Filter products based on the selected category
        val filteredProducts = sampleProducts.filter { it.id == categoryId }

        productsAdapter = ProductsAdapter(this,filteredProducts,this)
        binding.rvProducts.adapter = productsAdapter
        // Set LayoutManager to GridLayoutManager with 2 columns
        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
    }


    private fun addProductToCart(product: Product, quantity: Int) {
        selectedProducts[product] = quantity
         showCartBottomSheet() // Show cart bottom sheet when a product is added
    }





    private fun showCartBottomSheet() {
        if (selectedProducts.isNotEmpty()) {
            val bottomSheetDialog = BottomSheetDialog(this)
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_cart, null)
            bottomSheetDialog.setContentView(view)

            val containerProducts: LinearLayout = view.findViewById(R.id.containerProducts)
             btnAddToCart = view.findViewById(R.id.btnAddToCart)


            populateCartSummary(containerProducts)


            btnAddToCart.setOnClickListener {
                handleAddToCartAction(bottomSheetDialog)
            }



            btnAddToCart.setOnClickListener {
                // Handle add to cart action
                // For example, you can clear the selectedProducts list or navigate to another activity
                selectedProducts.clear()
//                bottomSheetDialog.dismiss()
            }


            // Now that btnAddToCart is initialized, pass it to the adapter
            productsAdapter.setCartButton(btnAddToCart)

            bottomSheetDialog.show()
        }
    }


    private fun populateCartSummary(containerProducts: LinearLayout) {
        selectedProducts.forEach { (product, quantity) ->
            val productView = LayoutInflater.from(this).inflate(R.layout.item_cart_product, null)
            val tvProductName: TextView = productView.findViewById(R.id.tvProductName)
            val tvProductDetails: TextView = productView.findViewById(R.id.tvProductDetails)

            val price = product.productPrice * quantity
            tvProductName.text = product.name
            tvProductDetails.text = "${product.name} $${product.productPrice} * $quantity = $price"

            containerProducts.addView(productView)
        }
    }


    private fun handleAddToCartAction(bottomSheetDialog: BottomSheetDialog) {
        selectedProducts.clear()
        bottomSheetDialog.dismiss()
    }
    override fun onAddToCartButtonClick(product: Product, quantity: Int) {
        addProductToCart(product, quantity)
    }

}
