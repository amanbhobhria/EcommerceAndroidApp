package com.example.pharmaecomapp.models

data class Product(
    val id: Int,
    val name: String,
    val imageResId: Int,
    var productPrice: Int,
    var productRandomId: String? = null,
    var productTitle: String? = null,
    var unit: String? = null,
    var productQuantity: Int?=null,
    var productStock: Int? = null,
    var productCategory: String? = null,
    var productType: String? = null,
    var itemCount: Int? = null,
    var adminUid: String? = null,
    var productImagesUris: ArrayList<String?>? = null,
)