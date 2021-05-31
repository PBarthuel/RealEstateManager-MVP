package com.openclassrooms.realestatemanager.app.modules.main.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.modules.createRealEstate.CreateRealEstateActivity
import com.openclassrooms.realestatemanager.app.modules.editRealEstate.EditRealEstateActivity
import com.openclassrooms.realestatemanager.app.modules.main.MainActivity
import com.openclassrooms.realestatemanager.app.modules.main.views.realEstateList.RealEstateListFragmentListener
import com.openclassrooms.realestatemanager.app.ui.photoList.adapter.PhotoListAdapter
import com.openclassrooms.realestatemanager.databinding.FragmentRealEstateMasterDetailBinding
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem
import com.openclassrooms.realestatemanager.presenter.modules.main.MainPresenter
import com.openclassrooms.realestatemanager.presenter.modules.main.views.RealEstateMasterDetailPresenter
import com.openclassrooms.realestatemanager.presenter.modules.main.views.RealEstateMasterDetailView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RealEstateMasterDetailFragment : Fragment(), RealEstateMasterDetailView {

    @Inject
    lateinit var presenter: RealEstateMasterDetailPresenter

    @Inject
    lateinit var adapter: PhotoListAdapter
    
    private var _binding: FragmentRealEstateMasterDetailBinding? = null
    private val binding get() = _binding!!

    private var detailFragmentLayout: FrameLayout? = null

    //region ActivityResult
    private val showEditRealEstateResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == MainActivity.RESULT_EDIT) {
                presenter.updateMasterDetail()
            }
        }
    //endregion

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentRealEstateMasterDetailBinding.inflate(layoutInflater)

        presenter.attach(this)
        setupAdapter()
        setHasOptionsMenu(true)
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUI()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (detailFragmentLayout == null) {
            inflater.inflate(R.menu.drawer_master_detail, menu)
        }
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (detailFragmentLayout == null) {
            when (item.itemId) {
                R.id.menu_activity_modification -> {
                    presenter.didSelectEdit()
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

    private fun setupAdapter() {
        binding.recyclerView.adapter = adapter
    }

    //region RealEstateMasterDetailView CallBacks
    override fun onSetupMasterDetail(item: UIRealEstateMasterDetailItem) {
        with(binding) {
            with(item) {
                descriptionContentTextView.text = description
                surfaceContentTextView.text = surface
                roomNumberContentTextView.text = totalRoomNumber
                bathroomNumberContentTextView.text = bathroomNumber
                bedroomNumberContentTextView.text = bedroomNumber
                locationRoadTextView.text = address.road
                locationCityTextView.text = address.city
                locationPostalCodeTextView.text = address.postalCode
                locationCountryTextView.text = address.country
                adapter.submitList(item.photos)
            }
        }
    }

    override fun onShowEditActivity(id: Long) {
        Intent(requireContext(), EditRealEstateActivity::class.java)
            .apply { putExtra(EditRealEstateActivity.INTENT_REAL_ESTATE_ID_DATA, id) }
            .also { intent -> showEditRealEstateResult.launch(intent) }
    }

    override fun displayToast() { Toast.makeText(requireContext(), getString(R.string.real_estate_edit_warning), Toast.LENGTH_SHORT).show() }
    //endregion
}