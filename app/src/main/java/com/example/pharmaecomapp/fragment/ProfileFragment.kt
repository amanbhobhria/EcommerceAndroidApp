package com.example.pharmaecomapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pharmaecomapp.R
import com.example.pharmaecomapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {


    private lateinit var binding: FragmentProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)        // Inflate the layout for this fragment
        onBackButtonClicked()
        return binding.root
    }

    private fun onBackButtonClicked() {
        binding.tbSearchFragment.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }
    }}
