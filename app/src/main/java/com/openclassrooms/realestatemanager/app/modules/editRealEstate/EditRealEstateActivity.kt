package com.openclassrooms.realestatemanager.app.modules.editRealEstate

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.core.view.isVisible
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.modules.addressSearch.AddressSearchActivity
import com.openclassrooms.realestatemanager.app.modules.createRealEstate.CreateRealEstateActivity
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
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem
import com.openclassrooms.realestatemanager.presenter.modules.editRealEstate.EditRealEstatePresenter
import com.openclassrooms.realestatemanager.presenter.modules.editRealEstate.EditRealEstateView
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import pl.aprilapps.easyphotopicker.ChooserType
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import javax.inject.Inject

@AndroidEntryPoint
class EditRealEstateActivity: AppCompatActivity(), EditRealEstateView, OnPhotoClickListener {

    @Inject
    lateinit var presenter: EditRealEstatePresenter

    @Inject
    lateinit var adapter: PhotoListAdapter

    private val binding by activityViewBinding(ActivityCreateRealEstateBinding::inflate)

    private lateinit var photoImagePicker: EasyImage

    //region ActivityResult
    private val showAddressSearchResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                CreateRealEstateActivity.RESULT_ADDRESS -> {
                    result.data?.getParcelableExtra<UIAddressItem>(AddressSearchActivity.INTENT_ADDRESS_ITEM_DATA)
                        ?.let { item -> presenter.didChooseAddress(item) }
                }
            }
            binding.addressEditText.clearFocus()
        }
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id: Long = intent.getLongExtra(INTENT_REAL_ESTATE_ID_DATA, 0)

        presenter.attach(this)

        presenter.setup(id)
        setupAdapter()
        setupUI()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        handlePhotoImagePickerActivityResult(requestCode, resultCode, data)
    }

    private fun setupUI() {
        with(binding) {
            soldConstraintLayout.isVisible = true

            photoImagePicker = EasyImage.Builder(this@EditRealEstateActivity)
                .setChooserTitle("Select a way")
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .build()

            if (Utils.isInternetAvailable(this@EditRealEstateActivity)) {
                addressViewSwitcher.displayedChild = 0
                addressEditText.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        Intent(applicationContext, AddressSearchActivity::class.java)
                            .also { intent -> showAddressSearchResult.launch(intent) }
                    }
                }
            } else {
                addressViewSwitcher.displayedChild = 1
                addressTextInputLayout.isVisible = false
            }

            photoButton.setClickWithDelay {
                photoImagePicker.openChooser(this@EditRealEstateActivity)
            }

            submitButton.text = getString(R.string.real_estate_edit_button)
            submitButton.setClickWithDelay {
                presenter.didSelectEdit(
                    type = typeEditText.text.toString(),
                    price = priceEditText.text.toString(),
                    surface = surfaceEditText.text.toString(),
                    description = descriptionEditText.text.toString(),
                    agent = agentEditText.text.toString(),
                    commerce = commerceSwitch.isChecked,
                    parc = parcSwitch.isChecked,
                    trainStation = trainStationSwitch.isChecked,
                    school = schoolSwitch.isChecked,
                    totalRoomNumber = totalRoomNumberEditText.text.toString(),
                    bedRoomNumber = bedroomNumberEditText.text.toString(),
                    bathRoomNumber = bathroomNumberEditText.text.toString(),
                    isSold = soldSwitch.isChecked
                )
            }
        }
    }

    private fun handlePhotoImagePickerActivityResult(requestCode: Int, resultCode: Int, resultIntent: Intent?) {
        photoImagePicker.handleActivityResult(requestCode, resultCode, resultIntent, this, object : EasyImage.Callbacks {
            override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                UCrop.of(Uri.fromFile(imageFiles[0].file), Uri.fromFile(imageFiles[0].file))
                    .withAspectRatio(1.0f, 1.0f)
                    .withMaxResultSize(
                        CreateRealEstateActivity.IMAGE_MAX_SIZE,
                        CreateRealEstateActivity.IMAGE_MAX_SIZE
                    )
                    .start(this@EditRealEstateActivity)
            }

            override fun onImagePickerError(error: Throwable, source: MediaSource) {
                Toast.makeText(this@EditRealEstateActivity, getString(R.string.error_photo_format), Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@EditRealEstateActivity, getString(R.string.error_photo_format), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupAdapter() {
        binding.recyclerView.adapter = adapter
        adapter.onPhotoClickListener = this
    }

    override fun onDismissView() {
        Toast.makeText(this, "You have edit your RealEstate, congratulations !!!", Toast.LENGTH_LONG).show()
        setResult(MainActivity.RESULT_EDIT, intent)
        finish()
    }

    //region EditRealEstateView CallBack
    override fun onSetupUI(item: UIRealEstateMasterDetailItem) {
        with(binding) {
            with(item) {
                typeEditText.setText(type)
                priceEditText.setText(price)
                surfaceEditText.setText(surface)
                descriptionEditText.setText(description)
                agentEditText.setText(agent)
                commerceSwitch.isChecked = commerce
                parcSwitch.isChecked = parc
                trainStationSwitch.isChecked = trainStation
                schoolSwitch.isChecked = school
                addressEditText.setText(address.road)
                totalRoomNumberEditText.setText(totalRoomNumber)
                bedroomNumberEditText.setText(bedroomNumber)
                bathroomNumberEditText.setText(bathroomNumber)
                soldSwitch.isChecked = isSold
                adapter.submitList(photos)
            }
        }
    }

    override fun onUpdateList(list: List<UIPhotoItem>) {
        adapter.notifyDataSetChanged()
        adapter.submitList(list)
    }

    override fun onShowAddress(address: String) { binding.addressEditText.setText(address) }
    //endregion

    //region OnPhotoClickListener Callback
    override fun onRealEstatePhotoClicked(item: UIPhotoItem) {
        presenter.didDeletePhoto(item)
    }
    //endregion

    companion object {
        const val INTENT_REAL_ESTATE_ID_DATA = "intent_real_estate_id_data"
    }
}