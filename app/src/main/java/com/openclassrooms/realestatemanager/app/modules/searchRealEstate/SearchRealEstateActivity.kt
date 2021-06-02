package com.openclassrooms.realestatemanager.app.modules.searchRealEstate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.modules.main.MainActivity
import com.openclassrooms.realestatemanager.app.modules.main.views.realEstateList.adapter.OnRealEstateClickListener
import com.openclassrooms.realestatemanager.app.modules.main.views.realEstateList.adapter.RealEstateListAdapter
import com.openclassrooms.realestatemanager.app.utils.viewBindings.activityViewBinding
import com.openclassrooms.realestatemanager.app.utils.viewExtension.getFormatWithoutTime
import com.openclassrooms.realestatemanager.app.utils.viewExtension.setClickWithDelay
import com.openclassrooms.realestatemanager.databinding.ActivitySearchRealEstateBinding
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateCondenseItem.UIRealEstateCondenseItem
import com.openclassrooms.realestatemanager.presenter.modules.searchRealEstate.SearchRealEstatePresenter
import com.openclassrooms.realestatemanager.presenter.modules.searchRealEstate.SearchRealEstateView
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class SearchRealEstateActivity: AppCompatActivity(), SearchRealEstateView, OnRealEstateClickListener {
    
    @Inject
    lateinit var presenter: SearchRealEstatePresenter
    
    @Inject
    lateinit var adapter: RealEstateListAdapter
    
    private val binding by activityViewBinding(ActivitySearchRealEstateBinding::inflate)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        presenter.attach(this)
        setupAdapter()
        setupUI()
        presenter.setup()
    }
    
    private fun setupUI() {
        with(binding) {
            val manager = supportFragmentManager
            val calendarConstraint = CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointBackward.now())
                    .build()
            
            //entryDatePicker
            entryDateButton.setClickWithDelay {
                val entryDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText(title)
                        .setCalendarConstraints(calendarConstraint)
                        .build()
                entryDatePicker.show(manager, "show entry date datePicker")
                entryDatePicker.addOnPositiveButtonClickListener {
                    presenter.didSelectEntryDate(Date(it))
                }
            }
            
            //exitDatePicker
            exitDateButton.setClickWithDelay {
                val exitDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText(title)
                        .setCalendarConstraints(calendarConstraint)
                        .build()
                exitDatePicker.show(manager, "show exit date datePicker")
                exitDatePicker.addOnPositiveButtonClickListener {
                    presenter.didSelectExitDate(Date(it))
                }
            }
    
            schoolSwitch.setOnCheckedChangeListener { _, isChecked ->
                presenter.didSelectSchool(isChecked)
            }
            
            commerceSwitch.setOnCheckedChangeListener { _, isChecked ->
                presenter.didSelectCommerce(isChecked)
            }
            
            parcSwitch.setOnCheckedChangeListener { _, isChecked ->
                presenter.didSelectParc(isChecked)
            }
            
            trainStationSwitch.setOnCheckedChangeListener { _, isChecked ->
                presenter.didSelectTrainStation(isChecked)
            }
            
            soldSwitch.setOnCheckedChangeListener { _, isChecked ->
                presenter.didSelectIsSold(isChecked)
            }
            
            minSurfaceEditText.doOnTextChanged { text, _, _, _ ->
                presenter.didSelectMinSurface(text.toString())
            }
            
            maxSurfaceEditText.doOnTextChanged { text, _, _, _ ->
                presenter.didSelectMaxSurface(text.toString())
            }
            
            minPriceEditText.doOnTextChanged { text, _, _, _ ->
                presenter.didSelectMinPrice(text.toString())
            }
            
            maxPriceEditText.doOnTextChanged { text, _, _, _ ->
                presenter.didSelectMaxPrice(text.toString())
            }
            
            photoNumberEditText.doOnTextChanged { text, _, _, _ ->
                presenter.didSelectMinPhotoNumber(text.toString())
            }
            
            validationButton.setClickWithDelay {
                presenter.didValidate(
                    cityEditText.text.toString()
                )
            }
        }
    }
    
    //region SearchRealEstateView Callback
    override fun onSetEntryDate(entryDate: Date) {
        binding.entryDateButton.text = Date(entryDate.time).getFormatWithoutTime(this)
    }
    
    override fun onSetExitDate(exitDate: Date) {
        binding.exitDateButton.text = Date(exitDate.time).getFormatWithoutTime(this)
    } //endregion
    
    override fun onDisplaySearch(list: List<UIRealEstateCondenseItem>) {
        adapter.submitList(list)
        presenter.reloadList()
    }
    
    override fun onResetAllSwitch() {
        with(binding) {
            schoolSwitch.isChecked = false
            commerceSwitch.isChecked = false
            parcSwitch.isChecked = false
            trainStationSwitch.isChecked = false
            soldSwitch.isChecked = false
            presenter.reloadSwitchValue()
        }
    }
    
    override fun onResetDate() {
        binding.entryDateButton.text = getString(R.string.real_estate_entry_date)
        binding.exitDateButton.text = getString(R.string.real_estate_exit_date)
    }
    
    private fun setupAdapter() {
        with(binding.recyclerView) {
            adapter = this@SearchRealEstateActivity.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        adapter.onRealEstateClickListener = this
    }
    
    override fun onRealEstateClicked(item: UIRealEstateCondenseItem) {
        val result = MainActivity.RESULT_SEARCH
    
        Intent().apply { putExtra(INTENT_ADDRESS_ITEM_DATA, item.id) }
                .also { intent ->
                    setResult(result, intent)
                    finish()
                }
    }
    
    companion object {
        const val INTENT_ADDRESS_ITEM_DATA = "intent_id_item_data"
    }
}