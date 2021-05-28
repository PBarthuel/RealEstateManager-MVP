package com.openclassrooms.realestatemanager.app.modules.realEstateCreate

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.app.utils.viewBindings.activityViewBinding
import com.openclassrooms.realestatemanager.app.utils.viewExtension.setClickWithDelay
import com.openclassrooms.realestatemanager.databinding.ActivityRealEstateCreateBinding
import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.UIAddressItem
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem
import com.openclassrooms.realestatemanager.presenter.modules.createRealEstate.CreateRealEstatePresenter
import com.openclassrooms.realestatemanager.presenter.modules.createRealEstate.CreateRealEstateView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RealEstateCreateActivity: AppCompatActivity(), CreateRealEstateView {
    
    @Inject
    lateinit var presenter: CreateRealEstatePresenter
    
    private val binding by activityViewBinding(ActivityRealEstateCreateBinding::inflate)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setupUI()
    }
    
    private fun setupUI() {
        with(binding) {
            submitButton.setClickWithDelay {
                presenter.didSubmitRealEstate(
                    type = typeEditText.text.toString(),
                    price = priceEditText.text.toString(),
                    surface = surfaceEditText.text.toString(),
                    description = descriptionEditText.text.toString(),
                    interestPoint = interestPointEditText.text.toString(),
                    agent = agentEditText.text.toString(),
                    totalRoomNumber = totalRoomNumberEditText.text.toString(),
                    bedroomNumber = bedroomNumberEditText.text.toString(),
                    bathroomNumber = bathroomNumberEditText.text.toString(),
                    UIAddressItem(
                        country = countryEditText.text.toString(),
                        road = roadEditText.text.toString(),
                        houseNumber = houseNumberEditText.text.toString(),
                        city = cityEditText.text.toString(),
                        postalCode = postalCodeEditText.text.toString()
                    )
                )
            }
        }
    }
    
    //region CreateRealEstateView callbacks
    override fun onDismissView() { finish() }
    //endregion
}