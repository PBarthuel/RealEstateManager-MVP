package com.openclassrooms.realestatemanager.app.modules.createRealEstate

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.core.view.isVisible
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.modules.addressSearch.AddressSearchActivity
import com.openclassrooms.realestatemanager.app.modules.main.MainActivity
import com.openclassrooms.realestatemanager.app.ui.photoList.adapter.OnPhotoClickListener
import com.openclassrooms.realestatemanager.app.ui.photoList.adapter.PhotoListAdapter
import com.openclassrooms.realestatemanager.app.ui.popups.AddingPhotoPopUpDialogFragment
import com.openclassrooms.realestatemanager.app.utils.Utils
import com.openclassrooms.realestatemanager.app.utils.viewBindings.activityViewBinding
import com.openclassrooms.realestatemanager.app.utils.viewExtension.setClickWithDelay
import com.openclassrooms.realestatemanager.databinding.ActivityCreateRealEstateBinding
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
import kotlin.math.log

@AndroidEntryPoint
class CreateRealEstateActivity: AppCompatActivity(), CreateRealEstateView, OnPhotoClickListener {

    @Inject
    lateinit var adapter: PhotoListAdapter
    
    @Inject
    lateinit var presenter: CreateRealEstatePresenter
    
    private val binding by activityViewBinding(ActivityCreateRealEstateBinding::inflate)
    
    private lateinit var photoImagePicker: EasyImage

    //region ActivityResult
    private val showAddressSearchResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_ADDRESS -> {
                    result.data?.getParcelableExtra<UIAddressItem>(AddressSearchActivity.INTENT_ADDRESS_ITEM_DATA)
                        ?.let { item -> presenter.didChooseAddress(item) }
                }
            }
            binding.addressEditText.clearFocus()
        }
    //endregion
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.attach(this)

        setupAdapter()
        setupUI()
        presenter.setup()
    }
    
    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        handlePhotoImagePickerActivityResult(requestCode, resultCode, data)
    }
    
    private fun setupUI() {
        with(binding) {
            photoImagePicker = EasyImage.Builder(this@CreateRealEstateActivity)
                .setChooserTitle("Select a way")
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .build()

            binding.addressEditText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    Intent(applicationContext, AddressSearchActivity::class.java)
                        .also { intent -> showAddressSearchResult.launch(intent) }
                }
            }

            photoButton.setClickWithDelay {
                photoImagePicker.openChooser(this@CreateRealEstateActivity)
            }

            submitButton.setClickWithDelay {
                presenter.didSubmitRealEstate(
                    type = typeEditText.text.toString(),
                    price = priceEditText.text.toString(),
                    surface = surfaceEditText.text.toString(),
                    description = descriptionEditText.text.toString(),
                    school = schoolSwitch.isChecked,
                    commerce = commerceSwitch.isChecked,
                    parc = parcSwitch.isChecked,
                    trainStation = trainStationSwitch.isChecked,
                    agent = agentEditText.text.toString(),
                    totalRoomNumber = totalRoomNumberEditText.text.toString(),
                    bedroomNumber = bedroomNumberEditText.text.toString(),
                    bathroomNumber = bathroomNumberEditText.text.toString(),
                )
            }
        }
    }
    
    private fun handlePhotoImagePickerActivityResult(requestCode: Int, resultCode: Int, resultIntent: Intent?) {
        photoImagePicker.handleActivityResult(requestCode, resultCode, resultIntent, this, object : EasyImage.Callbacks {
            override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                UCrop.of(Uri.fromFile(imageFiles[0].file), Uri.fromFile(imageFiles[0].file))
                    .withAspectRatio(1.0f, 1.0f)
                    .withMaxResultSize(IMAGE_MAX_SIZE, IMAGE_MAX_SIZE)
                    .start(this@CreateRealEstateActivity)
            }
            
            override fun onImagePickerError(error: Throwable, source: MediaSource) {
                Toast.makeText(this@CreateRealEstateActivity, getString(R.string.error_photo_format), Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@CreateRealEstateActivity, getString(R.string.error_photo_format), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupAdapter() {
        binding.recyclerView.adapter = adapter
        adapter.onPhotoClickListener = this
    }
    
    //region CreateRealEstateView callbacks
    override fun onDismissView() {
        Toast.makeText(this, "You have created a RealEstate, congratulations !!!", Toast.LENGTH_LONG).show()
        setResult(MainActivity.RESULT_CREATE, intent)
        finish()
    }

    override fun onUpdateList(list: List<UIPhotoItem>) {
        adapter.notifyDataSetChanged()
        adapter.submitList(list)
    }

    override fun onShowAddress(address: String) { binding.addressEditText.setText(address) }

    override fun onShowAddressEditText() {
        //TODO regarder les exceptions de rx + regarder le probleme du view switcher et aussi pour le commit tu as gerer internet
        Log.d("courgette", "onShowAddressEditText() called")
    }

    override fun onHideAddressEditText() {
        Log.d("courgette", "onHideAddressEditText() called")
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
    //endregion

    //region OnPhotoClickListener callback
    override fun onRealEstatePhotoClicked(item: UIPhotoItem) {
        presenter.didDeletePhoto(item)
    }
    //endregion

    companion object {
        const val IMAGE_MAX_SIZE = 512
        const val RESULT_ADDRESS = 100
    }
}