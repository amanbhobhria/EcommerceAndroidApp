package com.example.pharmaecomapp.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.pharmaecomapp.R
import com.example.pharmaecomapp.adapters.BrandFilterAdapter
import com.example.pharmaecomapp.adapters.CategoryFilterAdapter
import com.example.pharmaecomapp.databinding.FragmentFilterDialogBinding
import com.example.pharmaecomapp.models.Brands
import com.example.pharmaecomapp.models.Category
import com.example.pharmaecomapp.models.PriceRange
import com.example.pharmaecomapp.utils.Utils
import com.example.pharmaecomapp.viewModel.UserViewModel
import kotlinx.coroutines.launch


class FilterDialogFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    private lateinit var adapterBrandFilter: BrandFilterAdapter
    private lateinit var adapterCatFilter: CategoryFilterAdapter
    private lateinit var binding: FragmentFilterDialogBinding
    private var brandsList: List<Brands> = emptyList()
    private var categoryList: List<Category> = emptyList()
    private var activeCat: Int = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentFilterDialogBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        setupListView()
        setupSearchBar()
        setupApplyButton()
        backFunction()
        return binding.root
    }

    private fun backFunction() {
        binding.btnBackFilter.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
    }


    private fun setupApplyButton() {
        binding.btnApply.setOnClickListener {
            when (activeCat) {
                1 -> { // Brands section is active
                    val selectedBrands = adapterBrandFilter.getSelectedBrands()
                    sendFilterDataToHomeFragment(selectedBrands = selectedBrands)
                    Toast.makeText(requireContext(), "Changes applied: Brands", Toast.LENGTH_SHORT)
                        .show()
                }

                2 -> { // Category section is active
                    val selectedCategories = adapterCatFilter.getSelectedCategory()
                    sendFilterDataToHomeFragment(categories = selectedCategories)
                    Toast.makeText(
                        requireContext(),
                        "Changes applied: Categories",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                3 -> { // Price section is active
                    val selectedPriceRange = getSelectedPriceRange()
                    sendFilterDataToHomeFragment(priceRange = selectedPriceRange)
                    Toast.makeText(
                        requireContext(),
                        "Changes applied: Price Range",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

//            parentFragmentManager.popBackStack()

            Utils.loadFragment(1, requireActivity(),HomeFragment(),
                R.id.fragmentContainerView2,
                R.id.nav_home
            )


        }
    }


    private fun sendFilterDataToHomeFragment(
        selectedBrands: List<Brands>? = null,
        categories: List<Category>? = null,
        priceRange: PriceRange? = null
    ) {
        val filterData = Bundle().apply {
            selectedBrands?.let { putParcelableArrayList("selectedBrands", ArrayList(it)) }
            categories?.let { putParcelableArrayList("selectedCategories", ArrayList(it)) }
            priceRange?.let {
                putDouble("minPrice", it.minPrice)
                putDouble("maxPrice", it.maxPrice)
            }
        }

        setFragmentResult("filterRequestKey", filterData)
    }


    private fun showBrandsList() {
        lifecycleScope.launch {
            viewModel.fetchProductType().collect { brands ->
                brandsList = brands
                binding.tvBrandsQty.text = brandsList.size.toString()
                adapterBrandFilter = BrandFilterAdapter(requireContext(), brandsList)
                binding.listViewFilter.adapter = adapterBrandFilter
            }
        }

    }


    private fun showCatgryList() {
        lifecycleScope.launch {
            viewModel.fetchCategory().collect { category ->
                categoryList = category
                binding.tvCatgeoryQty.text = categoryList.size.toString()
                adapterCatFilter = CategoryFilterAdapter(requireContext(), categoryList)
                binding.listViewFilter.adapter = adapterCatFilter
            }
        }

    }


    private fun setupSearchBar() {

        binding.etSearchBrands.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                when (activeCat) {
                    1 -> filterBrands(s.toString())
                    2 -> filterCategory(s.toString())
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }


    private fun filterBrands(query: String) {
        val filteredList = brandsList.filter {
            it.name?.contains(query, ignoreCase = true) == true
        }
        adapterBrandFilter = BrandFilterAdapter(requireContext(), filteredList)
        binding.listViewFilter.adapter = adapterBrandFilter
    }


    private fun filterCategory(query: String) {
        val filteredList = categoryList.filter {
            it.name.contains(query, ignoreCase = true)
        }
        adapterCatFilter = CategoryFilterAdapter(requireContext(), filteredList)
        binding.listViewFilter.adapter = adapterCatFilter
    }


    private fun setupListView() {
        showBrandsList() // Initial setup for the brands list

        binding.brandsFlyt.setOnClickListener {
            setActiveCategory(1, "Search Brands", View.VISIBLE, View.GONE)
            showBrandsList()
        }

        binding.catFlyt.setOnClickListener {
            setActiveCategory(2, "Search Category", View.VISIBLE, View.GONE)
            showCatgryList()
            setupSearchBar()
        }

        binding.priceFlyt.setOnClickListener {
            setActiveCategory(3, null, View.GONE, View.VISIBLE)
        }
    }


    private fun setActiveCategory(
        category: Int,
        hint: String?,
        brandVisibility: Int,
        priceVisibility: Int
    ) {
        activeCat = category

        binding.brandsFlyt.setBackgroundColor(getColorForCategory(1))
        binding.catFlyt.setBackgroundColor(getColorForCategory(2))
        binding.priceFlyt.setBackgroundColor(getColorForCategory(3))

        binding.etSearchBrands.hint = hint ?: ""
        binding.brandsSection.visibility = brandVisibility
        binding.priceSection.visibility = priceVisibility
    }

    private fun getColorForCategory(category: Int): Int {
        return if (activeCat == category) {
            resources.getColor(R.color.white)
        } else {
            resources.getColor(R.color.app_color_lg)
        }
    }



    private fun getSelectedPriceRange(): PriceRange? {
        val checkedId = binding.radioGroupPriceRanges.checkedRadioButtonId
        return when (checkedId) {
            R.id.radioBtn0To50 -> PriceRange(minPrice = 0.0, maxPrice = 50.0)
            R.id.radioBtnUnder100 -> PriceRange(minPrice = 0.0, maxPrice = 100.0)
            R.id.radioBtnUnder200 -> PriceRange(minPrice = 0.0, maxPrice = 200.0)
            R.id.radioBtnUnder500 -> PriceRange(minPrice = 0.0, maxPrice = 500.0)
            R.id.radioBtnUnder1000 -> PriceRange(minPrice = 0.0, maxPrice = 1000.0)
            R.id.radioBtnAbove1000 -> PriceRange(minPrice = 1000.0, maxPrice = Double.MAX_VALUE)
            else -> null
        }
    }


}






