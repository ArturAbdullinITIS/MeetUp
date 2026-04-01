package ru.tbank.petcare.presentation.screen.registration

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tbank.petcare.domain.model.ErrorType
import ru.tbank.petcare.domain.usecase.RegisterUseCase
import ru.tbank.petcare.domain.usecase.SignInWithGoogleUseCase
import ru.tbank.petcare.utils.ErrorParser
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val errorParser: ErrorParser
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    fun processCommand(command: RegistrationCommand) {
        when (command) {
            is RegistrationCommand.InputEmail -> {
                _state.update { state ->
                    state.copy(
                        email = command.email,
                        emailError = ""
                    )
                }
            }

            is RegistrationCommand.InputPassword -> {
                _state.update { state ->
                    state.copy(
                        password = command.password,
                        passwordError = ""
                    )
                }
            }

            is RegistrationCommand.InputRepeatPassword -> {
                _state.update { state ->
                    state.copy(
                        repeatPassword = command.repeatPassword,
                        repeatPasswordError = ""
                    )
                }
            }

            is RegistrationCommand.ChangePasswordVisibility -> {
                _state.update { state ->
                    state.copy(isPasswordVisibility = !state.isPasswordVisibility)
                }
            }

            is RegistrationCommand.ChangeRepeatPasswordVisibility -> {
                _state.update { state ->
                    state.copy(isRepeatPasswordVisibility = !state.isRepeatPasswordVisibility)
                }
            }

            RegistrationCommand.RegisterUserFromEmailAndPassword -> registerWithEmail()
            is RegistrationCommand.SignInWithGoogle -> signInWithGoogle(command.context)
        }
    }

    private fun registerWithEmail() {
        val currentState = _state.value

        _state.update {
            it.copy(
                isLoading = true,
                emailError = "",
                passwordError = "",
                repeatPasswordError = "",
                error = ""
            )
        }

        viewModelScope.launch {
            val result = registerUseCase(
                email = currentState.email,
                password = currentState.password,
                repeatPassword = currentState.repeatPassword
            )

            if (result.isSuccess) {
                _state.update { it.copy(isSuccess = true, error = "", isLoading = false) }
            } else {
                val message = errorParser.getErrorMessage(result.error)

                val (emailError, passwordError, repeatPasswordError) =
                    if (result.error is ErrorType.AuthValidation) {
                        val msg = message
                        Triple(msg, msg, msg)
                    } else {
                        Triple("", "", "")
                    }

                _state.update {
                    it.copy(
                        isSuccess = false,
                        isLoading = false,
                        emailError = emailError,
                        passwordError = passwordError,
                        repeatPasswordError = repeatPasswordError,
                        error = message
                    )
                }
            }
        }
    }

    private fun signInWithGoogle(context: Context) {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = signInWithGoogleUseCase(context = context)

            if (result.isSuccess) {
                _state.update { it.copy(isSuccess = true, error = "", isLoading = false) }
            } else {
                _state.update {
                    it.copy(
                        isSuccess = false,
                        error = errorParser.getErrorMessage(result.error),
                        isLoading = false
                    )
                }
            }
        }
    }
}

sealed interface RegistrationCommand {
    data class InputEmail(val email: String) : RegistrationCommand
    data class InputPassword(val password: String) : RegistrationCommand
    data class InputRepeatPassword(val repeatPassword: String) : RegistrationCommand
    data class ChangePasswordVisibility(val isVisible: Boolean) : RegistrationCommand
    data class ChangeRepeatPasswordVisibility(val isVisible: Boolean) : RegistrationCommand
    data object RegisterUserFromEmailAndPassword : RegistrationCommand
    data class SignInWithGoogle(val context: Context) : RegistrationCommand
}
