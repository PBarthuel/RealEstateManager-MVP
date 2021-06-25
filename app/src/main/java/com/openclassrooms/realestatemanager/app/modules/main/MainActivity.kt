package com.openclassrooms.realestatemanager.app.modules.main

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.modules.main.views.realEstateList.RealEstateListFragment
import com.openclassrooms.realestatemanager.app.modules.main.views.RealEstateMasterDetailFragment
import com.openclassrooms.realestatemanager.app.modules.main.views.RealEstateMasterDetailFragmentListener
import com.openclassrooms.realestatemanager.app.modules.main.views.realEstateList.RealEstateListFragmentListener
import com.openclassrooms.realestatemanager.app.modules.map.MapActivity
import com.openclassrooms.realestatemanager.app.modules.searchRealEstate.SearchRealEstateActivity
import com.openclassrooms.realestatemanager.app.ui.popups.GeolocationPopUpDialog
import com.openclassrooms.realestatemanager.app.utils.showAppSettings
import com.openclassrooms.realestatemanager.presenter.modules.main.MainPresenter
import com.openclassrooms.realestatemanager.presenter.modules.main.MainView
import com.openclassrooms.realestatemanager.presenter.protocols.errorProtocol.PermissionErrorProtocol
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity(), MainView, RealEstateListFragmentListener, RealEstateMasterDetailFragmentListener, PermissionErrorProtocol {

    @Inject
    lateinit var presenter: MainPresenter
    
    private val listFragment = RealEstateListFragment()
    private val masterDetailFragment = RealEstateMasterDetailFragment()
    
    private var detailFragmentLayout: FrameLayout? = null
    
    //region ActivityResult
    private val showMapRealEstateResult: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if(result.resultCode == RESULT_MAP) {
                    result.data?.getLongExtra(MapActivity.INTENT_ID_ITEM_DATA, 0)
                        ?.let { id ->
                            if(detailFragmentLayout == null) {
                                displayedFragment(1)
                            }
                            masterDetailFragment.presenter.setup(id)
                        }
                }
            }
    //endregion
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.attach(this)

        setupToolBarAndMenuDrawer()
        setupUI(savedInstanceState)
        presenter.setup()
    }
    
    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }
    
    private fun setupToolBarAndMenuDrawer() {
        val toolbar = findViewById<Toolbar>(R.id.mainToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val drawer = findViewById<DrawerLayout>(R.id.mainDrawerLayout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }
    
    private fun setupUI(savedInstanceState: Bundle?) {
        val navView = findViewById<NavigationView>(R.id.activityMainMenuNavigationView)
        val drawer = findViewById<DrawerLayout>(R.id.mainDrawerLayout)
        detailFragmentLayout = findViewById(R.id.detailFragment)
        if (savedInstanceState == null) {
            if (detailFragmentLayout == null) {
                setupFragmentsPortrait()
                displayedFragment(0)
                navView.setNavigationItemSelectedListener { menuItem: MenuItem ->
                    if (menuItem.itemId == R.id.navRealEstateList) {
                        displayedFragment(0)
                        drawer.closeDrawer(GravityCompat.START)
                    }
                    if (menuItem.itemId == R.id.navRealEstateMap) {
                        drawer.closeDrawer(GravityCompat.START)
                        Intent(this@MainActivity, MapActivity::class.java)
                                .also { intent -> showMapRealEstateResult.launch(intent) }
                    }
                    false
                }
            } else {
                setupFragmentsLandscape()
                navView.setNavigationItemSelectedListener { menuItem: MenuItem ->
                    if (menuItem.itemId == R.id.navRealEstateMap) {
                        drawer.closeDrawer(GravityCompat.START)
                        Intent(this@MainActivity, MapActivity::class.java)
                                .also { intent -> showMapRealEstateResult.launch(intent) }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            displayedFragment(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun displayNoInternet() {
        Toast.makeText(this, "no internat", Toast.LENGTH_SHORT).show()
        //TODO enlever ça et mettre internet la ou il faut
    }

    override fun displayInternet() {
        Toast.makeText(this, "internat", Toast.LENGTH_SHORT).show()
    }

    //region GeoLocPermissionsView Callbacks
    override fun onReceiveBackgroundLocationMissingOnAndroidRError() {
        GeolocationPopUpDialog {
            showAppSettings()
        }.show(supportFragmentManager)
    }
    //endregion

    //region RealEstateListFragmentListener Callback
    override fun didClickRealEstate(id: Long) {
        if(detailFragmentLayout == null) {
            displayedFragment(1)
        }
        masterDetailFragment.presenter.setup(id)
    }

    override fun didReturnFromEditList() {
        masterDetailFragment.presenter.updateMasterDetail()
    }
    
    override fun didReturnFromSearchRealEstate(id: Long) {
        if(detailFragmentLayout == null) {
            displayedFragment(1)
        }
        masterDetailFragment.presenter.setup(id)
    } //endregion

    //regionRealEstateMasterDetailFragmentListener Callback
    override fun didReturnFromEditMasterDetail() {
        if(detailFragmentLayout == null) {
            displayedFragment(0)
        }
        listFragment.presenter.setup()
    }
    //endregion

    companion object {
        const val RESULT_CREATE = 100
        const val RESULT_EDIT = 101
        const val RESULT_SEARCH = 102
        const val RESULT_MAP = 103
    }
}