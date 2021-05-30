package com.openclassrooms.realestatemanager.data.vendors.dexter

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.openclassrooms.realestatemanager.data.models.entities.permissions.AccessPermission
import com.openclassrooms.realestatemanager.data.models.entities.permissions.AndroidPermissionConvertible
import com.openclassrooms.realestatemanager.data.models.entities.permissions.DataMultiplePermission
import com.openclassrooms.realestatemanager.data.models.entities.permissions.toData
import com.openclassrooms.realestatemanager.data.models.exceptions.DataPermissionException
import dagger.hilt.android.qualifiers.ActivityContext
import io.reactivex.rxjava3.core.Single
import java.util.ArrayList
import javax.inject.Inject

interface PermissionServiceApi {
    fun getPermissionsStatus(permissions: List<AccessPermission>): DataMultiplePermission
    fun requestPermissions(permissions: List<AndroidPermissionConvertible>): Single<DataMultiplePermission>
}

class PermissionServiceApiImpl @Inject constructor(
    private val permissionManager: DexterBuilder.Permission,
    @ActivityContext private val context: Context
) : PermissionServiceApi {

    override fun getPermissionsStatus(permissions: List<AccessPermission>): DataMultiplePermission {
        val grantedPermissions: ArrayList<AccessPermission> = arrayListOf()
        val deniedPermissions: ArrayList<AccessPermission> = arrayListOf()
        permissions.map { accessPermission ->
            if (ContextCompat.checkSelfPermission(context, accessPermission.toAndroidPermission()) == PackageManager.PERMISSION_GRANTED) {
                grantedPermissions.add(accessPermission)
            } else {
                deniedPermissions.add(accessPermission)
            }
        }
        return DataMultiplePermission(grantedPermissions.toList(), deniedPermissions.toList())
    }

    override fun requestPermissions(permissions: List<AndroidPermissionConvertible>): Single<DataMultiplePermission> =
        Single.create { single ->
            permissionManager
                .withPermissions(permissions.map { it.toAndroidPermission() })
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        when (report) {
                            null -> single.onError(DataPermissionException.ReportError("Permission report is null"))
                            else -> single.onSuccess(report.toData())
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }
                }).check()
        }
}
