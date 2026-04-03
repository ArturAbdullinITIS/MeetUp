package ru.tbank.petcare.presentation.screen.publicProfiles

import ru.tbank.petcare.presentation.model.PublicPetCardUIModel

data class PublicProfilesState(
    val pets: List<PublicPetCardUIModel> = emptyList(),
    val isPetsLoading: Boolean = false,
    val errorMessage: String? = null
)
