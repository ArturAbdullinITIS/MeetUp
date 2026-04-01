package ru.tbank.petcare.domain.usecase

import android.util.Patterns.EMAIL_ADDRESS
import ru.tbank.petcare.domain.model.ErrorType
import ru.tbank.petcare.domain.model.ValidationResult
import ru.tbank.petcare.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    companion object {
        private const val MIN_PASSWORD_LENGTH = 6
    }
    suspend operator fun invoke(email: String, password: String): ValidationResult<Unit> {
        val validationError = validateLoginFields(email = email, password = password)
        if (validationError != null) {
            return ValidationResult(isSuccess = false, error = validationError)
        }

        return authRepository.signInWithEmailAndPassword(email, password)
    }

    private fun validateLoginFields(email: String, password: String): ErrorType? {
        return when {
            email.isBlank() -> ErrorType.AuthValidation("Email is blank")
            !EMAIL_ADDRESS.matcher(email).matches() -> ErrorType.AuthValidation("Email is invalid")
            password.isBlank() -> ErrorType.AuthValidation("Password is blank")
            password.length < MIN_PASSWORD_LENGTH -> ErrorType.AuthValidation(
                "Password is too short (min $MIN_PASSWORD_LENGTH)"
            )
            else -> null
        }
    }
}
