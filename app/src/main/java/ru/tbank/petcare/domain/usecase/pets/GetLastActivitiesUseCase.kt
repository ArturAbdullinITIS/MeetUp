package ru.tbank.petcare.domain.usecase.pets

import ru.tbank.petcare.domain.repository.ActivityRepository
import javax.inject.Inject

class GetLastActivitiesUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {
    suspend operator fun invoke(petId: String, limit: Int) = activityRepository.getLastActivitiesByPetId(petId, limit)
}
