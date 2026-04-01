package ru.tbank.petcare.utils

import ru.tbank.petcare.R
import ru.tbank.petcare.domain.model.ErrorType

class ErrorParser(
    private val resourceProvider: ResourceProvider
) {
    fun getErrorMessage(error: ErrorType?): String {
        return when (error) {
            is ErrorType.AuthValidation -> resourceProvider.getString(
                R.string.authentication_error,
                error.message
            )

            is ErrorType.AuthConflict -> resourceProvider.getString(
                R.string.authentication_error,
                error.message
            )

            is ErrorType.AuthCredentials -> resourceProvider.getString(
                R.string.authentication_error,
                error.message
            )

            is ErrorType.AuthCancelled -> resourceProvider.getString(
                R.string.authentication_error,
                error.message
            )

            is ErrorType.AuthUnknown -> resourceProvider.getString(
                R.string.authentication_error,
                error.message
            )

            is ErrorType.CommonError -> resourceProvider.getString(
                R.string.common_error,
                error.message
            )

            is ErrorType.NetworkError -> resourceProvider.getString(
                R.string.network_error,
                error.message
            )

            null -> resourceProvider.getString(R.string.unknown_error)
            is ErrorType.FirebaseAuthenticationError -> resourceProvider.getString(
                R.string.authentication_error,
                error.message
            )
        }
    }
}
