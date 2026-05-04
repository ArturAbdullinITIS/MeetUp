package ru.tbank.petcare.utils

import android.util.Patterns.EMAIL_ADDRESS
import ru.tbank.petcare.R
import ru.tbank.petcare.domain.model.ErrorType
import javax.inject.Inject

class AuthFieldsValidator @Inject constructor(
    private val resourceProvider: ResourceProvider
) {

    fun validateEmail(email: String): ErrorType? {
        return when {
            email.isBlank() -> ErrorType.AuthValidation(
                resourceProvider.getString(R.string.error_email_blank)
            )
            !EMAIL_ADDRESS.matcher(email).matches() -> ErrorType.AuthValidation(
                resourceProvider.getString(R.string.error_email_invalid)
            )
            else -> null
        }
    }

    fun validatePassword(password: String): ErrorType? {
        return when {
            password.isBlank() -> ErrorType.AuthValidation(
                resourceProvider.getString(R.string.error_password_short)
            )
            password.length < MIN_PASSWORD_LENGTH -> ErrorType.AuthValidation(
                resourceProvider.getString(R.string.error_password_short)
            )
            else -> null
        }
    }
    fun validateRepeatPassword(password: String, repeatPassword: String): ErrorType? {
        return when {
            repeatPassword.isBlank() -> ErrorType.AuthValidation(
                resourceProvider.getString(R.string.repeat_password)
            )
            password != repeatPassword -> ErrorType.AuthValidation(
                resourceProvider.getString(R.string.error_passwords_do_not_match)
            )
            else -> null
        }
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }
}
