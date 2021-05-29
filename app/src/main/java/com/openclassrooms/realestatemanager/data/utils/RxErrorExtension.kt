package com.openclassrooms.realestatemanager.data.utils

import androidx.annotation.VisibleForTesting
import com.openclassrooms.realestatemanager.data.models.domainMappingProtocols.DomainExceptionConvertible
import com.openclassrooms.realestatemanager.data.models.exceptions.DataAPIDecodeException
import com.openclassrooms.realestatemanager.domain.models.DomainException
import com.openclassrooms.realestatemanager.domain.models.DomainNetworkException
import com.openclassrooms.realestatemanager.domain.models.error.DomainError
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.serialization.SerializationException

fun Completable.throwDomainExceptionOnError(): Completable = doOnError { throw getDomainException(it) }
fun <T> Observable<T>.throwDomainExceptionOnError(): Observable<T> = doOnError { throw getDomainException(it) }
fun <T> Single<T>.throwDomainExceptionOnError(): Single<T> = doOnError { throw getDomainException(it) }

@VisibleForTesting
internal fun getDomainException(error: Throwable?): DomainException =
    when (error) {
        is SerializationException, is DataAPIDecodeException ->
            DomainNetworkException.InternalError(
                DomainError(error.message ?: "SerializationException or DataAPIDecodeException")
            )
        is DomainExceptionConvertible -> error.toDomain()
        else -> DomainNetworkException.InternalError(
            DomainError(error?.message ?: "DomainNetworkException")
        )
    }
