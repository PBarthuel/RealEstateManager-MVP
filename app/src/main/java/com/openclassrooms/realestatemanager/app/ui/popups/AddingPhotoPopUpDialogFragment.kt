package com.openclassrooms.realestatemanager.app.ui.popups

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.DialogFragmentAddingPhotoPopupBinding
import java.io.File

class AddingPhotoPopUpDialogFragment(
    private val photoFile: File?,
    private val completion: ((String, String) -> Unit)?
) : DialogFragment() {

    private var _binding: DialogFragmentAddingPhotoPopupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = DialogFragmentAddingPhotoPopupBinding.inflate(layoutInflater)

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

            BitmapFactory.decodeFile(photoFile?.absolutePath)?.let { imageView.setImageBitmap(it) }

            submitButton.setOnClickListener {
                photoFile?.let {
                    completion?.invoke(typeEditText.text.toString(), convertImageToBase64(it))
                }
                dismiss()
            }
            cancelButton.setOnClickListener {
                dismiss()
            }
        }
    }

    fun show(fragmentManager: FragmentManager) {
        setStyle(STYLE_NORMAL, R.style.InformationPopupDialogTheme)
        show(fragmentManager, this::class.java.toString())
    }

    private fun convertImageToBase64(file: File): String =
        file.let { Base64.encodeToString(file.readBytes(), Base64.DEFAULT) }
}
