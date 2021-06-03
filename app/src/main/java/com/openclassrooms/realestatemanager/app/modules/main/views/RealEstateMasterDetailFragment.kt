package com.openclassrooms.realestatemanager.app.modules.main.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.modules.addressSearch.AddressSearchActivity
import com.openclassrooms.realestatemanager.app.modules.editRealEstate.EditRealEstateActivity
import com.openclassrooms.realestatemanager.app.modules.main.MainActivity
import com.openclassrooms.realestatemanager.app.modules.searchRealEstate.SearchRealEstateActivity
import com.openclassrooms.realestatemanager.app.ui.photoList.adapter.PhotoListAdapter
import com.openclassrooms.realestatemanager.databinding.FragmentRealEstateMasterDetailBinding
import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.UIAddressItem
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem
import com.openclassrooms.realestatemanager.presenter.modules.main.views.RealEstateMasterDetailPresenter
import com.openclassrooms.realestatemanager.presenter.modules.main.views.RealEstateMasterDetailView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

interface RealEstateMasterDetailFragmentListener {
    fun didReturnFromEditMasterDetail()
}

@AndroidEntryPoint
class RealEstateMasterDetailFragment : Fragment(), RealEstateMasterDetailView {

    @Inject
    lateinit var presenter: RealEstateMasterDetailPresenter

    @Inject
    lateinit var adapter: PhotoListAdapter
    
    private var _binding: FragmentRealEstateMasterDetailBinding? = null
    private val binding get() = _binding!!

    private var detailFragmentLayout: FrameLayout? = null

    private var listener: RealEstateMasterDetailFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = context as RealEstateMasterDetailFragmentListener
    }

    //region ActivityResult
    private val showEditorSearchRealEstateResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when(result.resultCode) {
                MainActivity.RESULT_EDIT -> {
                    presenter.updateMasterDetail()
                    listener?.didReturnFromEditMasterDetail()
                }
                MainActivity.RESULT_SEARCH -> {
                    result.data?.getLongExtra(SearchRealEstateActivity.INTENT_ID_ITEM_DATA, 0)
                            ?.let { id -> presenter.setup(id) }
                }
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
    
    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
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
                    Intent(requireContext(), SearchRealEstateActivity::class.java)
                        .also { intent -> showEditorSearchRealEstateResult.launch(intent) }
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
            .also { intent -> showEditorSearchRealEstateResult.launch(intent) }
    }

    override fun displayToast() { Toast.makeText(requireContext(), getString(R.string.real_estate_edit_warning), Toast.LENGTH_SHORT).show() }
    override fun onShowStaticMap(url: String) {
        Glide.with(binding.staticMapImageView).load(url).into(binding.staticMapImageView) }
    //endregion
}