package com.openclassrooms.realestatemanager.app.modules.main.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.modules.realEstateCreate.RealEstateCreateActivity
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
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_activity_creation -> {
                Intent(context, RealEstateCreateActivity::class.java).also { startActivity(it) }
            }
            R.id.menu_activity_modification -> {
                if (binding.descriptionContentTextView.text.isNullOrEmpty()) {
                    // TODO here when no estate is selected discplay toast
                } else {
                    // TODO intent for future editActivity
                }
            }
            R.id.menu_activity_search -> {
                // TODO intent for future searchActivity
            }
        }
        return super.onOptionsItemSelected(item)
    }
}