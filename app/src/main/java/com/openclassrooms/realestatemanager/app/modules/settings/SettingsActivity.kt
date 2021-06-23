package com.openclassrooms.realestatemanager.app.modules.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.app.modules.main.MainActivity
import com.openclassrooms.realestatemanager.app.utils.viewBindings.activityViewBinding
import com.openclassrooms.realestatemanager.databinding.ActivitySettingsBinding
import com.openclassrooms.realestatemanager.presenter.modules.settings.SettingsPresenter
import com.openclassrooms.realestatemanager.presenter.modules.settings.SettingsView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity: AppCompatActivity(), SettingsView {
    
    @Inject
    lateinit var presenter: SettingsPresenter
    
    private val binding by activityViewBinding(ActivitySettingsBinding::inflate)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        presenter.attach(this)
        
        presenter.setup()
        setupUI()
    }
    
    private fun setupUI() {
        binding.euroOrDollarSwitch.setOnClickListener {
            presenter.didTapOnSwitch(binding.euroOrDollarSwitch.isChecked)
        }
    }
    
    //region SettingsView Callbacks
    override fun onShowSwitchState(isEuro: Boolean) {
        binding.euroOrDollarSwitch.isChecked = isEuro
    }
    
    override fun dismissView() {
        setResult(MainActivity.RESULT_SETTINGS, intent)
        finish()
    }
    //region
}