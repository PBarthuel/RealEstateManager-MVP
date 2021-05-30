package com.openclassrooms.realestatemanager.app.ui.popups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.DialogGeolocationPopupBinding

class GeolocationPopUpDialog(
    private val completion: (() -> Unit)?
) : DialogFragment() {

    private var _binding: DialogGeolocationPopupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = DialogGeolocationPopupBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        isCancelable = false
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        with(binding) {

            enableButton.setOnClickListener {
                completion?.invoke()
                dismiss()
            }
            cancelButton.setOnClickListener {
                dismiss()
            }
        }
    }

    fun show(fragmentManager: FragmentManager) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.InformationPopupDialogTheme)
        show(fragmentManager, this::class.java.toString())
    }
}