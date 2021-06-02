package com.openclassrooms.realestatemanager.app.modules.searchRealEstate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.openclassrooms.realestatemanager.app.utils.viewBindings.activityViewBinding
import com.openclassrooms.realestatemanager.app.utils.viewExtension.getFormatWithoutTime
import com.openclassrooms.realestatemanager.databinding.ActivitySearchRealEstateBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class SearchRealEstateActivity: AppCompatActivity() {
    
    private val binding by activityViewBinding(ActivitySearchRealEstateBinding::inflate)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setupUI()
    }
    
    private fun setupUI() {
        with(binding) {
            val manager = supportFragmentManager
            val calendarConstraint = CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointBackward.now())
                    .build()
            
            //entryDatePicker
            entryDateButton.setOnClickListener {
                val entryDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText(title)
                        .setCalendarConstraints(calendarConstraint)
                        .build()
                entryDatePicker.show(manager, "show entry date datePicker")
                entryDatePicker.addOnPositiveButtonClickListener {
                    entryDateButton.text = Date(it).getFormatWithoutTime(root.context)
                }
            }
            
            //exitDatePicker
            exitDateButton.setOnClickListener {
                val exitDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText(title)
                        .setCalendarConstraints(calendarConstraint)
                        .build()
                exitDatePicker.show(manager, "show exit date datePicker")
                exitDatePicker.addOnPositiveButtonClickListener {
                    exitDateButton.text = Date(it).getFormatWithoutTime(root.context)
                }
            }
        }
    }
}