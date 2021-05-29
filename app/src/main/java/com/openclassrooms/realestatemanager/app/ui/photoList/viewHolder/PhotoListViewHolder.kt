package com.openclassrooms.realestatemanager.app.ui.photoList.viewHolder

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.app.ui.photoList.adapter.OnPhotoClickListener
import com.openclassrooms.realestatemanager.databinding.ViewHolderPhotoListBinding
import com.openclassrooms.realestatemanager.presenter.models.uiPhotoItem.UIPhotoItem

class PhotoListViewHolder(
    private val binding: ViewHolderPhotoListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: UIPhotoItem, onRealEstatePhotoClickListener: OnPhotoClickListener?) {
        with(binding) {
            titleTextView.text = item.roomName
            val decodedString = Base64.decode(item.photoReference, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)?.let { imageView.setImageBitmap(it) }
            listConstraintLayout.setOnClickListener {
                onRealEstatePhotoClickListener?.onRealEstatePhotoClicked(item)
            }
        }
    }
}