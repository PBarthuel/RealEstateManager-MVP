package com.openclassrooms.realestatemanager.app.modules.addressSearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.app.utils.viewBindings.activityViewBinding
import com.openclassrooms.realestatemanager.databinding.ActivityAddressSearchBinding

class AddressSearchActivity: AppCompatActivity() {

    private val binding by activityViewBinding(ActivityAddressSearchBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}