package com.example.pharmaecomapp.utils

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.pharmaecomapp.activity.HomeActivity

object  Utils {


    fun loadFragment(
        id:Int,
        activity: FragmentActivity,
        fragment: Fragment,
        containerId: Int,
        itemId: Int
    ) {
        // Replace the current fragment with the new one
        activity.supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(null) // Optional: Add this fragment to the back stack
            .commit()

        // Optionally, update BottomNavigationView selection if needed
        // This part is commented out as it depends on your specific implementation
        // Optionally, update BottomNavigationView selection if itemId is provided


            if (activity is HomeActivity&&id==2) {
                try {
                    Log.e("UtilsError", "No issue in utils")

                    activity.binding.bottomNavigation.selectedItemId = itemId

                } catch (e: Exception) {
                    Toast.makeText(activity,e.toString(),Toast.LENGTH_SHORT).show()
                    Log.e("UtilsError", e.toString())
                }
            }
    }
}
