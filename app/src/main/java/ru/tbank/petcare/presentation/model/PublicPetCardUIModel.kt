package ru.tbank.petcare.presentation.model

import androidx.annotation.StringRes

data class PublicPetCardUIModel(
    val id: String,
    val name: String,
    val photoUrl: String,
    val note: String,
    val gameScore: Int,
    @StringRes val gender: Int,
    val breed: String,
)
