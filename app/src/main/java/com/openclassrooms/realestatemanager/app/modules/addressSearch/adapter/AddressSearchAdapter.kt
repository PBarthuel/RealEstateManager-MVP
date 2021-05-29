package com.openclassrooms.realestatemanager.app.modules.addressSearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.openclassrooms.realestatemanager.app.modules.addressSearch.viewHolder.AddressSearchViewHolder
import com.openclassrooms.realestatemanager.databinding.ViewHolderAddressSearchBinding
import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.UIAddressItem
import javax.inject.Inject

class AddressSearchAdapter @Inject constructor() : ListAdapter<UIAddressItem, AddressSearchViewHolder>(UIAddressItemDiffUtil) {

    var onAddressClickListener: OnAddressClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressSearchViewHolder =
        AddressSearchViewHolder(ViewHolderAddressSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: AddressSearchViewHolder, position: Int) {
        holder.onBind(getItem(position), onAddressClickListener)
    }

    private object UIAddressItemDiffUtil : DiffUtil.ItemCallback<UIAddressItem>() {
        override fun areItemsTheSame(oldItem: UIAddressItem, newItem: UIAddressItem): Boolean =
            oldItem.road == newItem.road
                    && oldItem.city == newItem.city

        override fun areContentsTheSame(oldItem: UIAddressItem, newItem: UIAddressItem): Boolean =
            oldItem.road == newItem.road
                    && oldItem.city == newItem.city
    }
}
