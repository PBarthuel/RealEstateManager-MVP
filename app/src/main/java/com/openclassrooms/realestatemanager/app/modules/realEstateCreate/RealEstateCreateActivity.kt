package com.openclassrooms.realestatemanager.app.modules.realEstateCreate

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.ui.popups.AddingPhotoPopUpDialogFragment
import com.openclassrooms.realestatemanager.app.utils.viewBindings.activityViewBinding
import com.openclassrooms.realestatemanager.app.utils.viewExtension.setClickWithDelay
import com.openclassrooms.realestatemanager.databinding.ActivityRealEstateCreateBinding
import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.UIAddressItem
import com.openclassrooms.realestatemanager.presenter.models.uiPhotoItem.UIPhotoItem
import com.openclassrooms.realestatemanager.presenter.modules.createRealEstate.CreateRealEstatePresenter
import com.openclassrooms.realestatemanager.presenter.modules.createRealEstate.CreateRealEstateView
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import pl.aprilapps.easyphotopicker.ChooserType
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import javax.inject.Inject

@AndroidEntryPoint
class RealEstateCreateActivity: AppCompatActivity(), CreateRealEstateView {
    
    @Inject
    lateinit var presenter: CreateRealEstatePresenter
    
    private val binding by activityViewBinding(ActivityRealEstateCreateBinding::inflate)
    
    private lateinit var photoImagePicker: EasyImage
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setupUI()
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        handlePhotoImagePickerActivityResult(requestCode, resultCode, data)
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
            
            photoButton.setClickWithDelay {
                photoImagePicker = EasyImage.Builder(this@RealEstateCreateActivity)
                    .setChooserTitle("Select a way")
                    .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                    .build()
                    .also { picker -> picker.openChooser(this@RealEstateCreateActivity) }
            }
        }
    }
    
    private fun handlePhotoImagePickerActivityResult(requestCode: Int, resultCode: Int, resultIntent: Intent?) {
        photoImagePicker.handleActivityResult(requestCode, resultCode, resultIntent, this, object : EasyImage.Callbacks {
            override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                UCrop.of(Uri.fromFile(imageFiles[0].file), Uri.fromFile(imageFiles[0].file))
                        .withAspectRatio(1.0f, 1.0f)
                        .withMaxResultSize(IMAGE_MAX_SIZE, IMAGE_MAX_SIZE)
                        .start(this@RealEstateCreateActivity)
            }
            
            override fun onImagePickerError(error: Throwable, source: MediaSource) {
                Toast.makeText(this@RealEstateCreateActivity, getString(R.string.error_photo_format), Toast.LENGTH_SHORT).show()
            }
            
            override fun onCanceled(source: MediaSource) {}
        })
        
        if (requestCode == UCrop.REQUEST_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                resultIntent?.let { data ->
                    val file = UCrop.getOutput(data)?.toFile()
                    AddingPhotoPopUpDialogFragment(file) { photoName, base64ImageData ->
                        presenter.didAddPhoto(photoName, base64ImageData)
                    }.show(supportFragmentManager)
                }
            } else if (resultCode == UCrop.RESULT_ERROR) {
                Toast.makeText(this@RealEstateCreateActivity, getString(R.string.error_photo_format), Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    //region CreateRealEstateView callbacks
    override fun onDismissView() { finish() }
    override fun onUpdateList(list: List<UIPhotoItem>) { }
    //endregion
    
    companion object {
        const val IMAGE_MAX_SIZE = 512
    }
}