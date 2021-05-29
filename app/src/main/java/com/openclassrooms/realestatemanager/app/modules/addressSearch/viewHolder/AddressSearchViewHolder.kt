package com.openclassrooms.realestatemanager.app.modules.addressSearch.viewHolder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.app.modules.addressSearch.adapter.OnAddressClickListener
import com.openclassrooms.realestatemanager.databinding.ViewHolderAddressSearchBinding
import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.UIAddressItem

class AddressSearchViewHolder(
    private val binding: ViewHolderAddressSearchBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: UIAddressItem, delegateOn: OnAddressClickListener?) {
        with(binding) {
            roadTextView.text = item.road
            if (item.city.isEmpty()) {
                cityTextView.isVisible = false
            } else {
                cityTextView.isVisible = true
                cityTextView.text = item.city
            }
            containerConstraintLayout.setOnClickListener {
                delegateOn?.didSelectItem(item)
            }
        }
    }
}
