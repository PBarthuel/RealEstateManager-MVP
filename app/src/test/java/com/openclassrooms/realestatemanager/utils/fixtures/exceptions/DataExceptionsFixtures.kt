package com.openclassrooms.realestatemanager.utils.fixtures.exceptions

import com.openclassrooms.realestatemanager.data.models.entities.error.ErrorResponse
import com.openclassrooms.realestatemanager.data.models.exceptions.DataAPIDecodeException
import com.openclassrooms.realestatemanager.data.models.exceptions.DataPermissionException

class DataExceptionsFixtures {

    //region DataAPIException
    class DataAPIExceptionUtils {
        companion object {
            fun create(message: String): DataAPIDecodeException =
                DataAPIDecodeException(message)
        }
    }
    //endregion

    //region DataPermissionException
    class DataPermissionExceptionUtils {
        companion object {
            fun createReportError(message: String): DataPermissionException =
                DataPermissionException.ReportError(
                    message
                )
        }
    }
    //endregion

    //region Error
    class ErrorResponseUtils {
        companion object {
            fun create(data: Map<String, String>? = null): ErrorResponse =
                ErrorResponse(
                    FixturesExceptionsConstants.Error.message,
                    data
                )
        }
    }
    //endregion
}
