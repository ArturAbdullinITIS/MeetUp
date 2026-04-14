package ru.tbank.petcare.domain.usecase.pets

import android.util.Log
import ru.tbank.petcare.domain.model.ValidationResult
import ru.tbank.petcare.domain.repository.PetsRepository
import javax.inject.Inject

class UpdatePetHighScoreUseCase @Inject constructor(
    private val petsRepository: PetsRepository
) {
    suspend operator fun invoke(petId: String, newScore: Int): ValidationResult<Unit> {
        Log.d("UpdatePetHighScoreUseCase", "invoke update high score use case ")
        return petsRepository.updatePetHighScore(petId, newScore)
    }
}
