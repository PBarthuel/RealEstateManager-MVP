package com.openclassrooms.realestatemanager.app.modules.main.views.realEstateList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.openclassrooms.realestatemanager.app.modules.main.views.realEstateList.viewHolder.RealEstateListViewHolder
import com.openclassrooms.realestatemanager.databinding.ViewHolderRealEstateListBinding
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateCondenseItem.UIRealEstateCondenseItem
import javax.inject.Inject

class RealEstateListAdapter @Inject constructor() : ListAdapter<UIRealEstateCondenseItem, RealEstateListViewHolder>(UIRealEstateCondenseItemDiffUtil){

    var onRealEstateClickListener: OnRealEstateClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealEstateListViewHolder =
        RealEstateListViewHolder(ViewHolderRealEstateListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RealEstateListViewHolder, position: Int) {
        holder.onBind(getItem(position), onRealEstateClickListener)
    }

    private object UIRealEstateCondenseItemDiffUtil : DiffUtil.ItemCallback<UIRealEstateCondenseItem>() {
        override fun areItemsTheSame(oldItem: UIRealEstateCondenseItem, newItem: UIRealEstateCondenseItem): Boolean =
            oldItem.id == newItem.id
                    && oldItem.city == newItem.city
                    && oldItem.price == newItem.price
                    && oldItem.type == newItem.type

        override fun areContentsTheSame(oldItem: UIRealEstateCondenseItem, newItem: UIRealEstateCondenseItem): Boolean =
            oldItem == newItem
    }
}

