package com.openclassrooms.realestatemanager.app.ui.photoList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.openclassrooms.realestatemanager.app.ui.photoList.viewHolder.PhotoListViewHolder
import com.openclassrooms.realestatemanager.databinding.ViewHolderPhotoListBinding
import com.openclassrooms.realestatemanager.presenter.models.uiPhotoItem.UIPhotoItem
import javax.inject.Inject

class PhotoListAdapter @Inject constructor() : ListAdapter<UIPhotoItem, PhotoListViewHolder>(UIPhotoItemDiffUtil) {

    var onPhotoClickListener: OnPhotoClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder =
        PhotoListViewHolder(ViewHolderPhotoListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        holder.onBind(getItem(position), onPhotoClickListener)
    }

    private object UIPhotoItemDiffUtil : DiffUtil.ItemCallback<UIPhotoItem>() {
        override fun areItemsTheSame(oldItem: UIPhotoItem, newItem: UIPhotoItem): Boolean =
            oldItem.photoReference == newItem.photoReference
                    && oldItem.roomName == newItem.roomName

        override fun areContentsTheSame(oldItem: UIPhotoItem, newItem: UIPhotoItem): Boolean =
            oldItem.photoReference == newItem.photoReference
                    && oldItem.roomName == newItem.roomName
    }
}