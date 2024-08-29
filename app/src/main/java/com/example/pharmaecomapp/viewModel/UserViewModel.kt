package com.example.pharmaecomapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.pharmaecomapp.models.Brands
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow


class UserViewModel(application: Application) : AndroidViewModel(application) {

    fun fetchProductType(): Flow<List<Brands>> = flow {
        // Simulate fetching data with a delay
        kotlinx.coroutines.delay(1000L) // Simulate network delay

        // Emit a list of demo brands
        val demoBrands = listOf(
            Brands(1,"Brand A", "https://via.placeholder.com/150"),
            Brands(2,"Brand B", "https://via.placeholder.com/150"),
            Brands(3,"Brand C", "https://via.placeholder.com/150"),
            Brands(4,"Brand D", "https://via.placeholder.com/150")
        )

        emit(demoBrands) // Emit the list to observers
    }



}