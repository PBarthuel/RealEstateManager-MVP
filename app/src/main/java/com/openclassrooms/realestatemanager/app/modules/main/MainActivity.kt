package com.openclassrooms.realestatemanager.app.modules.main

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.modules.main.views.RealEstateListFragment
import com.openclassrooms.realestatemanager.app.modules.main.views.RealEstateMasterDetailFragment

class MainActivity: AppCompatActivity() {
    
    private val listFragment = RealEstateListFragment()
    private val masterDetailFragment = RealEstateMasterDetailFragment()
    
    private var detailFragmentLayout: FrameLayout? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        setupToolBarAndMenuDrawer()
        setupUI(savedInstanceState)
    }
    
    private fun setupToolBarAndMenuDrawer() {
        val toolbar = findViewById<Toolbar>(R.id.mainToolbar)
        setSupportActionBar(toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.mainDrawerLayout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }
    
    private fun setupUI(savedInstanceState: Bundle?) {
        val navView = findViewById<NavigationView>(R.id.activityMainMenuNavigationView)
        detailFragmentLayout = findViewById(R.id.detailFragment)
        if (savedInstanceState == null) {
            if (detailFragmentLayout == null) {
                setupFragmentsPortrait()
                displayedFragment(0)
                navView.setNavigationItemSelectedListener { menuItem: MenuItem ->
                    if (menuItem.itemId == R.id.navRealEstateList) {
                        displayedFragment(0)
                    }
                    if (menuItem.itemId == R.id.navRealEstateMap) {
                        displayedFragment(1)
                    }
                    false
                }
            } else {
                setupFragmentsLandscape()
                navView.setNavigationItemSelectedListener { menuItem: MenuItem ->
                    if (menuItem.itemId == R.id.navRealEstateMap) {
                        displayedFragment(1)
                    }
                    false
                }
            }
        }
    }
    
    private fun setupFragmentsPortrait() {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, listFragment)
                .add(R.id.fragmentContainer, masterDetailFragment).hide(masterDetailFragment)
                .commit()
    }
    
    private fun setupFragmentsLandscape() {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, listFragment)
                .add(R.id.detailFragment, masterDetailFragment)
                .commit()
    }
    
    private fun displayedFragment(fragmentNumber: Int) {
        with(supportFragmentManager.beginTransaction()) {
            when(fragmentNumber) {
                0 -> show(listFragment).hide(masterDetailFragment)
                1 -> hide(listFragment).show(masterDetailFragment)
                else -> {}
            }
            commit()
        }
    }
}