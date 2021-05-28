package com.openclassrooms.realestatemanager.app.modules.main.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentRealEstateMasterDetailBinding

class RealEstateMasterDetailFragment : Fragment() {
    
    private var _binding: FragmentRealEstateMasterDetailBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentRealEstateMasterDetailBinding.inflate(layoutInflater)
    
        setHasOptionsMenu(true)
        
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.drawer_master_detail, menu)
    }
}