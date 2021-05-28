package com.openclassrooms.realestatemanager.app.modules.realEstateCreate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.app.utils.viewBindings.activityViewBinding
import com.openclassrooms.realestatemanager.databinding.ActivityRealEstateCreateBinding

class RealEstateCreateActivity: AppCompatActivity() {
    
    private val binding by activityViewBinding(ActivityRealEstateCreateBinding::inflate)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
    }
}