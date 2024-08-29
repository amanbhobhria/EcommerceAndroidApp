package com.example.pharmaecomapp.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmaecomapp.R
import com.example.pharmaecomapp.activity.ProductsActivity
import com.example.pharmaecomapp.adapters.BrandsAdapter
import com.example.pharmaecomapp.adapters.CategoriesAdapter
import com.example.pharmaecomapp.databinding.FragmentHomeBinding
import com.example.pharmaecomapp.models.Brands
import com.example.pharmaecomapp.models.Category
import com.example.pharmaecomapp.viewModel.UserViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    val viewModel: UserViewModel by viewModels()
    private lateinit var adapterBrands: BrandsAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setStatusBarColor()
        navigatingToSearchFragment()
        onProfileBtnClicked()
        fetchBrands()
        fetchCategoriesProduct()
        return binding.root
    }

    private fun fetchCategoriesProduct() {
        // Sample data to display in the RecyclerView
        val sampleCategories = listOf(
            Category(1, "Category 1", R.drawable.product_demo_img),
            Category(2, "Category 2", R.drawable.throat_img),
            Category(3, "Category 3", R.drawable.eyes),
            Category(4, "Category 4", R.drawable.face_prod)
        )


        categoriesAdapter = CategoriesAdapter(sampleCategories) { category ->
            // Handle category click and navigate to ProductsActivity
            val intent = Intent(requireContext(), ProductsActivity::class.java).apply {
                putExtra("CATEGORY_ID", category.id)
                putExtra("CATEGORY_NAME", category.name)
            }
            startActivity(intent)
        }


        binding.rvCategories.adapter = categoriesAdapter
        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
    }

    fun seeAllBtnClicked(productType: Brands) {

    }


    private fun fetchBrands() {
        binding.shimmerViewContainer.visibility = View.VISIBLE
        lifecycleScope.launch {

            viewModel.fetchProductType().collect {
                adapterBrands = BrandsAdapter(::seeAllBtnClicked)
                binding.rvBestselers.adapter = adapterBrands
                adapterBrands.differ.submitList(it)
                binding.shimmerViewContainer.visibility = View.GONE
            }

        }
    }


    private fun onProfileBtnClicked() {
        binding.ivProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }


    private fun navigatingToSearchFragment() {
        binding.searchCv.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }


    private fun setStatusBarColor() {
        activity?.window?.apply {
            val statusBarColors = ContextCompat.getColor(requireContext(), R.color.app_color)
            statusBarColor = statusBarColors
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

}