package com.openclassrooms.realestatemanager.app.modules.map.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem

class CustomInfoWindowAdapter (
    private val context: Context
) : GoogleMap.InfoWindowAdapter {
    
    var view = (context as Activity).layoutInflater.inflate(R.layout.view_holder_custom_info_window, null)
    
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun rendowWindowText(marker: Marker, view: View){
        val item = marker.tag as UIRealEstateMasterDetailItem
        
        val tvType = view.findViewById<TextView>(R.id.type)
        val tvRoad = view.findViewById<TextView>(R.id.road)
        val tvPrice = view.findViewById<TextView>(R.id.price)
        val ivImage = view.findViewById<ImageView>(R.id.image)
        val ivSold = view.findViewById<ImageView>(R.id.sold)
    
        tvType.text = item.type
        tvRoad.text = item.address.road
        tvPrice.text = item.price
        if(item.photos.isNotEmpty()) {
            val decodedString = Base64.decode(item.photos[0].photoReference, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)?.let { ivImage.setImageBitmap(it) }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivImage.setImageDrawable(context.getDrawable(R.drawable.img_empty_house))
            }else {
                ivImage.setImageDrawable(context.resources.getDrawable(R.drawable.img_empty_house))
            }
        }
        ivSold.isVisible = item.isSold
    }
    
    override fun getInfoWindow(p0: Marker): View? {
        rendowWindowText(p0, view)
        return view
    }
    
    override fun getInfoContents(p0: Marker): View? {
        rendowWindowText(p0, view)
        return view
    }
    
}