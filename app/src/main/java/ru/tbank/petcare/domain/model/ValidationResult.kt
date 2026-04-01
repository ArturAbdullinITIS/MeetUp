package ru.tbank.petcare.domain.model

data class ValidationResult<T>(
    val isSuccess: Boolean = false,
    val error: ErrorType? = null,
    val data: T? = null,
)

sealed class ErrorType(open val message: String = "") {
    data class NetworkError(override val message: String = "") : ErrorType(message)
    data class CommonError(override val message: String = "") : ErrorType(message)
    data class FirebaseAuthenticationError(override val message: String = "") : ErrorType(message)
    data class AuthValidation(override val message: String = "") : ErrorType(message)
    data class AuthConflict(override val message: String = "") : ErrorType(message)
    data class AuthCredentials(override val message: String = "") : ErrorType(message)
    data class AuthCancelled(override val message: String = "") : ErrorType(message)
    data class AuthUnknown(override val message: String = "") : ErrorType(message)
}
