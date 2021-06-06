package com.openclassrooms.realestatemanager.utils.fixtures.exceptions

import com.openclassrooms.realestatemanager.domain.models.DomainFormException
import com.openclassrooms.realestatemanager.domain.models.DomainNetworkException
import com.openclassrooms.realestatemanager.domain.models.DomainPermissionException
import com.openclassrooms.realestatemanager.domain.models.error.DomainError

class DomainExceptionsFixtures {

    //region DomainNetworkExceptions
    class DomainNetworkUtils {
        companion object {
            fun createInternalError(message: String) = DomainNetworkException.InternalError(DomainError(message))
        }
    }
    //endregion

    //region DomainFormExceptions
    class DomainFormExceptionUtils {
        companion object {
            fun createWrongTypeFormat(type: String) =
                DomainFormException.WrongTypeFormat("$type format is not valid")

            fun createWrongPriceFormat(price: String) =
                DomainFormException.WrongPriceFormat("$price format is not valid")
    
            fun createWrongSurfaceFormat(surface: String) =
                DomainFormException.WrongSurfaceFormat("$surface format is not valid")
    
            fun createWrongDescriptionFormat(description: String) =
                DomainFormException.WrongDescriptionFormat("$description format is not valid")
    
            fun createWrongAgentFormat(agent: String) =
                DomainFormException.WrongAgentFormat("$agent format is not valid")
    
            fun createWrongTotalRoomNumberFormat(totalRoomNumber: String) =
                DomainFormException.WrongTotalRoomNumberFormat("$totalRoomNumber format is not valid")
    
            fun createWrongBedroomNumberFormat(bedroomNumber: String) =
                DomainFormException.WrongBedRoomNumberFormat("$bedroomNumber format is not valid")
    
            fun createWrongBathroomNumberFormat(bathroomNumber: String) =
                DomainFormException.WrongBathRoomNumberFormat("$bathroomNumber format is not valid")
        }
    }
    //endregion

    //region DomainPermissionException
    class DomainPermissionExceptionUtils {
        companion object {
            fun createReportError(message: String): DomainPermissionException =
                DomainPermissionException.ReportError(
                    message
                )
        }
    }
    //endregion

    //region Error
    class DomainErrorUtils {
        companion object {
            fun create(data: Map<String, String>? = null): DomainError =
                DomainError(FixturesExceptionsConstants.Error.message,
                    data
                )
        }
    }
    //endregion
}
