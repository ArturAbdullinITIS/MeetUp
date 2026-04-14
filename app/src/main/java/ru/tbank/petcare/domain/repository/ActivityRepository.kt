package ru.tbank.petcare.domain.repository

import ru.tbank.petcare.domain.model.Activity
import ru.tbank.petcare.domain.model.ValidationResult

interface ActivityRepository {

    suspend fun createActivity(petId: String, activity: Activity): ValidationResult<Activity>

    suspend fun getActivitiesByPetId(
        petId: String
    ): ValidationResult<List<Activity>>

    suspend fun getLastActivitiesByPetId(
        petId: String,
        limit: Int
    ): ValidationResult<List<Activity>>

    suspend fun getActivitiesByPeriod(
        petId: String,
        startDate: Long,
        endDate: Long
    ): ValidationResult<List<Activity>>
}
