package com.openclassrooms.realestatemanager.app.modules.main.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.modules.createRealEstate.CreateRealEstateActivity
import com.openclassrooms.realestatemanager.databinding.FragmentRealEstateListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealEstateListFragment : Fragment() {
    
    private var _binding: FragmentRealEstateListBinding? = null
    private val binding get() = _binding!!

    private var detailFragmentLayout: FrameLayout? = null
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentRealEstateListBinding.inflate(layoutInflater)
        
        setHasOptionsMenu(true)
        
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    
        setupUI()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (detailFragmentLayout == null) {
            inflater.inflate(R.menu.drawer_list, menu)
        }
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (detailFragmentLayout == null) {
            when (item.itemId) {
                R.id.menu_activity_creation -> {
                    Intent(context, CreateRealEstateActivity::class.java).also { startActivity(it) }
                }
                R.id.menu_activity_search -> {
                    // TODO intent for future searchActivity
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    
    private fun setupUI() {
        detailFragmentLayout = view?.rootView?.findViewById(R.id.detailFragment)
    }
}