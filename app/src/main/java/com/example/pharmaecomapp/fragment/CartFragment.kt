package com.example.pharmaecomapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmaecomapp.R
import com.example.pharmaecomapp.adapters.CartAdapter
import com.example.pharmaecomapp.cart.CartManager
import com.example.pharmaecomapp.databinding.FragmentCartBinding


class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!


    private lateinit var cartAdapter: CartAdapter  // Create this adapter to show cart items

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the status bar color
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.app_color)
        setupRecyclerView()
        updateTotalPrice()
        checkOut()
    }

    private fun checkOut() {
        binding.btnCheckout.setOnClickListener {

            CartManager.clearCart() // Clear cart after order is placed
            requireActivity().supportFragmentManager.popBackStack()

            binding.emptyDataLyt.visibility = View.VISIBLE
            binding.rvCartItems.visibility = View.GONE // Hide empty data layout
            binding.checkoutLayout.visibility = View.GONE  // Show checkout layout


            navigateToHome( )

            }

    }

    private fun navigateToHome() {
        // Navigate to HomeFragment and remove CartFragment from the back stack
        val homeFragment = HomeFragment() // Assuming you have a HomeFragment class

        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView2, homeFragment) // Replace with your fragment container ID
            addToBackStack(null) // This adds the transaction to the back stack
            commit()
        }

        // Remove CartFragment from back stack
        requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(this, CartManager.getCartItems().toList().toMutableList())
        binding.rvCartItems.adapter = cartAdapter
        binding.rvCartItems.layoutManager = LinearLayoutManager(requireContext())
        // Check if the cart is empty and update the UI accordingly
        checkEmptyCart()

    }

    fun updateTotalPrice() {
        val totalPrice = CartManager.calculateTotalPrice()
        binding.tvTotalPrice.text = "Total: $${String.format("%.2f", totalPrice)}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkEmptyCart() {
        // Check if the cartAdapter's item list is empty
        if (cartAdapter.itemCount == 0) {
            binding.checkoutLayout.visibility = View.GONE  // Hide checkout layout
            binding.emptyDataLyt.visibility = View.VISIBLE  // Show empty data layout
        } else {
            binding.checkoutLayout.visibility = View.VISIBLE  // Show checkout layout
            binding.emptyDataLyt.visibility = View.GONE
            binding.rvCartItems.visibility = View.VISIBLE // Hide empty data layout
        }
    }

}

