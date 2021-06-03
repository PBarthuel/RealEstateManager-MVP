package com.openclassrooms.realestatemanager.app.modules.addressSearch

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.modules.addressSearch.adapter.AddressSearchAdapter
import com.openclassrooms.realestatemanager.app.modules.addressSearch.adapter.OnAddressClickListener
import com.openclassrooms.realestatemanager.app.modules.createRealEstate.CreateRealEstateActivity
import com.openclassrooms.realestatemanager.app.utils.showAppSettings
import com.openclassrooms.realestatemanager.app.utils.viewBindings.activityViewBinding
import com.openclassrooms.realestatemanager.app.utils.viewExtension.activityRootView
import com.openclassrooms.realestatemanager.app.utils.viewExtension.setClickWithDelay
import com.openclassrooms.realestatemanager.app.utils.viewExtension.showShortSnackbar
import com.openclassrooms.realestatemanager.databinding.ActivityAddressSearchBinding
import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.UIAddressItem
import com.openclassrooms.realestatemanager.presenter.modules.addressSearch.AddressSearchPresenter
import com.openclassrooms.realestatemanager.presenter.modules.addressSearch.AddressSearchView
import com.openclassrooms.realestatemanager.presenter.protocols.utils.NetworkSchedulers
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class AddressSearchActivity: AppCompatActivity(), AddressSearchView, OnAddressClickListener {

    @Inject
    lateinit var adapter: AddressSearchAdapter

    @Inject
    lateinit var presenter: AddressSearchPresenter

    @Inject
    lateinit var networkSchedulers: NetworkSchedulers

    override val rootView: ViewGroup? by lazy { activityRootView }

    private val binding by activityViewBinding(ActivityAddressSearchBinding::inflate)
    private val queryInputSubject = PublishSubject.create<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.attach(this)

        setupAdapter()
        setupUI()
    }
    
    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    private fun setupUI() {
        queryInputSubject
            .debounce(1, TimeUnit.SECONDS)
            .map { input -> presenter.searchAddresses(input) }
            .subscribeOn(networkSchedulers.io)
            .subscribe({}, {})

        with(binding) {
            queryEditText.doOnTextChanged { text, _, _, _ ->
                queryInputSubject.onNext(text.toString())
            }

            userLocationButton.setClickWithDelay {
                presenter.didSelectUserLocation()
            }
        }

    }

    private fun setupAdapter() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.recyclerView.context, LinearLayoutManager.VERTICAL, false)
        adapter.onAddressClickListener = this
    }

    //region AddressSearchView Callbacks
    override fun onShowEmptyAddresses() {
        with(binding) {
            emptyResultTextView.isVisible = true
            recyclerView.isVisible = false
        }
    }

    override fun onShowAddressesList(addresses: List<UIAddressItem>) {
        with(binding) {
            emptyResultTextView.isVisible = false
            recyclerView.isVisible = true
            adapter.submitList(addresses)
        }
    }

    override fun onShowMissingPermission() {
        rootView?.showShortSnackbar(getString(R.string.address_search_missing_location_permissions_snackbar_text), getString(R.string.common_ok)) {
            showAppSettings()
        }
    }

    override fun onReceiveUserAddress(address: UIAddressItem) {
        val result = CreateRealEstateActivity.RESULT_ADDRESS

        Intent().apply { putExtra(INTENT_ADDRESS_ITEM_DATA, address) }
            .also { intent ->
                setResult(result, intent)
                finish()
            }
    }

    //endregion

    //region OnAddressClickListener callback
    override fun didSelectItem(item: UIAddressItem) {
        val result = CreateRealEstateActivity.RESULT_ADDRESS

        Intent().apply { putExtra(INTENT_ADDRESS_ITEM_DATA, item) }
            .also { intent ->
                setResult(result, intent)
                finish()
            }
    }
    //endregion

    companion object {
        const val INTENT_ADDRESS_ITEM_DATA = "intent_address_item_data"
    }
}