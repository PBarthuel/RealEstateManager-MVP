package com.openclassrooms.realestatemanager.app.modules.main.views.realEstateList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.modules.createRealEstate.CreateRealEstateActivity
import com.openclassrooms.realestatemanager.app.modules.editRealEstate.EditRealEstateActivity
import com.openclassrooms.realestatemanager.app.modules.main.MainActivity
import com.openclassrooms.realestatemanager.app.modules.main.views.realEstateList.adapter.OnRealEstateClickListener
import com.openclassrooms.realestatemanager.app.modules.main.views.realEstateList.adapter.RealEstateListAdapter
import com.openclassrooms.realestatemanager.app.modules.searchRealEstate.SearchRealEstateActivity
import com.openclassrooms.realestatemanager.databinding.FragmentRealEstateListBinding
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateCondenseItem.UIRealEstateCondenseItem
import com.openclassrooms.realestatemanager.presenter.modules.main.views.realEstateList.RealEstateListPresenter
import com.openclassrooms.realestatemanager.presenter.modules.main.views.realEstateList.RealEstateListView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

interface RealEstateListFragmentListener {
    fun didClickRealEstate(id: Long)
    fun didReturnFromEditList()
}

@AndroidEntryPoint
class RealEstateListFragment : Fragment(), RealEstateListView, OnRealEstateClickListener {

    @Inject
    lateinit var presenter: RealEstateListPresenter

    @Inject
    lateinit var adapter: RealEstateListAdapter
    
    private var _binding: FragmentRealEstateListBinding? = null
    private val binding get() = _binding!!

    private var listener: RealEstateListFragmentListener? = null

    private var detailFragmentLayout: FrameLayout? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = context as RealEstateListFragmentListener
    }

    //region ActivityResult
    private val showCreateOrEditRealEstateResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when(result.resultCode) {
                MainActivity.RESULT_CREATE -> presenter.setup()
                MainActivity.RESULT_EDIT -> listener?.didReturnFromEditList()
            }
        }
    //endregion
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentRealEstateListBinding.inflate(layoutInflater)

        presenter.attach(this)

        setHasOptionsMenu(true)
        
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupUI()
        presenter.setup()
    }
    
    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (detailFragmentLayout == null) {
            inflater.inflate(R.menu.drawer_list, menu)
        } else {
            inflater.inflate(R.menu.drawer_full, menu)
        }
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (detailFragmentLayout == null) {
            when (item.itemId) {
                R.id.menu_activity_creation -> {
                    Intent(context, CreateRealEstateActivity::class.java).also {
                        intent -> showCreateOrEditRealEstateResult.launch(intent)
                    }
                }
                R.id.menu_activity_search -> {
                    Intent(requireContext(), SearchRealEstateActivity::class.java)
                            .also { intent -> startActivity(intent) }
                }
            }
        } else {
            when (item.itemId) {
                R.id.menu_activity_creation -> {
                    Intent(context, CreateRealEstateActivity::class.java).also {
                            intent -> showCreateOrEditRealEstateResult.launch(intent)
                    }
                }
                R.id.menu_activity_modification -> {
                    presenter.didSelectEdit()
                }
                R.id.menu_activity_search -> {
                    Intent(requireContext(), SearchRealEstateActivity::class.java)
                            .also { intent -> startActivity(intent) }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    
    private fun setupUI() {
        detailFragmentLayout = view?.rootView?.findViewById(R.id.detailFragment)
    }

    private fun setupAdapter() {
        with(binding.recyclerView) {
            adapter = this@RealEstateListFragment.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        adapter.onRealEstateClickListener = this
    }

    //region RealEstateListView Callbacks
    override fun onSetupRealEstates(list: List<UIRealEstateCondenseItem>) {
        adapter.submitList(list)
    }

    override fun onShowEditActivity(id: Long) {
        Intent(requireContext(), EditRealEstateActivity::class.java)
            .apply { putExtra(EditRealEstateActivity.INTENT_REAL_ESTATE_ID_DATA, id) }
            .also { intent -> showCreateOrEditRealEstateResult.launch(intent) }
    }

    override fun displayToast() { Toast.makeText(requireContext(), getString(R.string.real_estate_edit_warning), Toast.LENGTH_SHORT).show() }
    //endregion

    //region OnRealEstateClickListener Callback
    override fun onRealEstateClicked(item: UIRealEstateCondenseItem) {
        presenter.setupId(item.id)
        listener?.didClickRealEstate(item.id)
    }
    //endregion
}