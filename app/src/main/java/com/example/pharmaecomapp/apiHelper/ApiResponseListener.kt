package com.example.pharmaecomapp.apiHelper

import com.google.gson.JsonElement

interface ApiResponseListner {
    fun success(tag: String?, jsonElement: JsonElement?)
    fun failure(tag: String?, errorMessage: String?)
}



