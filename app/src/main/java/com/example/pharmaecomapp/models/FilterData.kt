package com.example.pharmaecomapp.models

data class FilterData(
    val brands: List<Brands>,
    val categories: List<Category>,
    val priceRange: PriceRange
)
