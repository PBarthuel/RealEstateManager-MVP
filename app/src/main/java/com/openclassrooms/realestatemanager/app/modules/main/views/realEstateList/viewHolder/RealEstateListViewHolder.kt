package com.openclassrooms.realestatemanager.app.modules.main.views.realEstateList.viewHolder

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.modules.main.views.realEstateList.adapter.OnRealEstateClickListener
import com.openclassrooms.realestatemanager.databinding.ViewHolderRealEstateListBinding
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateCondenseItem.UIRealEstateCondenseItem

class RealEstateListViewHolder(
    private val binding: ViewHolderRealEstateListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: UIRealEstateCondenseItem, onRealEstateClickListener: OnRealEstateClickListener?) {
        with(binding) {
            typeTextView.text = item.type
            if(item.city == "to enable") {
                cityTextView.text = ""
            } else {
                cityTextView.text = item.city
            }
            priceTextView.text = item.price
            //TODO temporaire getDrawable
            when(item.photo) {
                "" -> thumbnailImageView.setImageDrawable(root.context.getDrawable(R.drawable.img_empty_house))
                else -> {
                    val decodedString = Base64.decode(item.photo, Base64.DEFAULT)
                    BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)?.let { thumbnailImageView.setImageBitmap(it) }
                }
            }
            soldImageView.isVisible = item.isSold
            listConstraintLayout.setOnClickListener {
                onRealEstateClickListener?.onRealEstateClicked(item)
            }
        }
    }
}