package ru.tbank.petcare.domain.usecase

import android.util.Patterns.EMAIL_ADDRESS
import ru.tbank.petcare.domain.model.ErrorType
import ru.tbank.petcare.domain.model.ValidationResult
import ru.tbank.petcare.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    companion object {
        private const val MIN_PASSWORD_LENGTH = 6
    }
    suspend operator fun invoke(
        email: String,
        password: String,
        repeatPassword: String
    ): ValidationResult<Unit> {
        val validationError = validateRegisterFields(
            email = email,
            password = password,
            repeatPassword = repeatPassword
        )
        if (validationError != null) {
            return ValidationResult(isSuccess = false, error = validationError)
        }

        return authRepository.registerWithEmailAndPassword(email, password)
    }

    private fun validateRegisterFields(
        email: String,
        password: String,
        repeatPassword: String
    ): ErrorType? {
        return when {
            email.isBlank() -> ErrorType.AuthValidation("Email is blank")
            !EMAIL_ADDRESS.matcher(email).matches() -> ErrorType.AuthValidation("Email is invalid")
            password.isBlank() -> ErrorType.AuthValidation("Password is blank")
            password.length < MIN_PASSWORD_LENGTH -> ErrorType.AuthValidation(
                "Password is too short (min ${MIN_PASSWORD_LENGTH})"
            )
            repeatPassword.isBlank() -> ErrorType.AuthValidation("Repeat password is blank")
            password != repeatPassword -> ErrorType.AuthValidation("Passwords do not match")
            else -> null
        }
    }
}
