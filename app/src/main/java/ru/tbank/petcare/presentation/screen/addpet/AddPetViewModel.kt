package ru.tbank.petcare.presentation.screen.addpet

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.tbank.petcare.domain.usecase.AddPetUseCase
import ru.tbank.petcare.presentation.navigation.Route
import javax.inject.Inject

@HiltViewModel
class AddPetViewModel @Inject constructor(
    private val addPetUseCase: AddPetUseCase
): ViewModel() {

    private val _state = MutableStateFlow(AddPetState())
    val state = _state.asStateFlow()
}


sealed interface AddPetCommand {
    data class InputName(val name: String): AddPetCommand
    data class InputBreed(val breed: String): AddPetCommand
    data class InputWeight(val weight: String): AddPetCommand
    data class InputDateOfBirth(val dateOfBirth: String): AddPetCommand
    data class InputGender(val gender:String): AddPetCommand
    data class InputNotes(val notes: String): AddPetCommand
    data class InputIsPublic(val isPublic: Boolean): AddPetCommand
    object AddPet: AddPetCommand
}




data class AddPetState(
    val name: String = "",
    val breed: String = "",
    val weight: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val notes: String = "",
    val isPublic: Boolean = false,
)