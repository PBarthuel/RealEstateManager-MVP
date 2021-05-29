package com.openclassrooms.realestatemanager.app.modules.realEstateCreate

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.ui.photoList.adapter.OnPhotoClickListener
import com.openclassrooms.realestatemanager.app.ui.photoList.adapter.PhotoListAdapter
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
class RealEstateCreateActivity: AppCompatActivity(), CreateRealEstateView, OnPhotoClickListener {

    @Inject
    lateinit var adapter: PhotoListAdapter
    
    @Inject
    lateinit var presenter: CreateRealEstatePresenter
    
    private val binding by activityViewBinding(ActivityRealEstateCreateBinding::inflate)
    
    private lateinit var photoImagePicker: EasyImage
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.attach(this)

        setupAdapter()
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

    private fun setupAdapter() {
        binding.recyclerView.adapter = adapter
        adapter.onPhotoClickListener = this
    }
    
    //region CreateRealEstateView callbacks
    override fun onDismissView() { finish() }
    override fun onUpdateList(list: List<UIPhotoItem>) {
        adapter.notifyDataSetChanged()
        adapter.submitList(list)
    }

    override fun onReceiveWrongTypeFormatError() {
        binding.typeTextInputLayout.setErrorIconDrawable(R.drawable.ic_error)
        binding.typeTextInputLayout.error = "wrong type format"
    }
    override fun onReceiveWrongPriceFormatError() {
        binding.priceTextInputLayout.setErrorIconDrawable(R.drawable.ic_error)
        binding.priceTextInputLayout.error = "wrong price format"
    }
    override fun onReceiveWrongSurfaceFormatError() {
        binding.priceTextInputLayout.setErrorIconDrawable(R.drawable.ic_error)
        binding.priceTextInputLayout.error = "wrong surface format"
    }
    override fun onReceiveWrongDescriptionFormatError() {
        binding.priceTextInputLayout.setErrorIconDrawable(R.drawable.ic_error)
        binding.priceTextInputLayout.error = "wrong description format"
    }
    override fun onReceiveWrongAgentFormatError() {
        binding.priceTextInputLayout.setErrorIconDrawable(R.drawable.ic_error)
        binding.priceTextInputLayout.error = "wrong agent format"
    }
    override fun onReceiveWrongTotalRoomNumberFormatError() {
        binding.priceTextInputLayout.setErrorIconDrawable(R.drawable.ic_error)
        binding.priceTextInputLayout.error = "wrong total room number format"
    }
    override fun onReceiveWrongBedroomNumberFormatError() {
        binding.priceTextInputLayout.setErrorIconDrawable(R.drawable.ic_error)
        binding.priceTextInputLayout.error = "wrong bedroom number format"
    }
    override fun onReceiveWrongBathroomNumberFormatError() {
        binding.priceTextInputLayout.setErrorIconDrawable(R.drawable.ic_error)
        binding.priceTextInputLayout.error = "wrong bathroom number format"
    }
    override fun onReceiveWrongCountryFormatError() {
        binding.priceTextInputLayout.setErrorIconDrawable(R.drawable.ic_error)
        binding.priceTextInputLayout.error = "wrong country format"
    }
    override fun onReceiveWrongRoadFormatError() {
        binding.priceTextInputLayout.setErrorIconDrawable(R.drawable.ic_error)
        binding.priceTextInputLayout.error = "wrong road format"
    }
    override fun onReceiveWrongHouseNumberFormatError() {
        binding.priceTextInputLayout.setErrorIconDrawable(R.drawable.ic_error)
        binding.priceTextInputLayout.error = "wrong house number format"
    }
    override fun onReceiveWrongCityFormatError() {
        binding.priceTextInputLayout.setErrorIconDrawable(R.drawable.ic_error)
        binding.priceTextInputLayout.error = "wrong city format"
    }
    override fun onReceiveWrongPostalCodeFormatError() {
        binding.priceTextInputLayout.setErrorIconDrawable(R.drawable.ic_error)
        binding.priceTextInputLayout.error = "wrong postal code format"
    }
    //endregion

    //region OnPhotoClickListener callback
    override fun onRealEstatePhotoClicked(item: UIPhotoItem) {
        presenter.didDeletePhoto(item)
    }
    //endregion

    companion object {
        const val IMAGE_MAX_SIZE = 512
    }
}