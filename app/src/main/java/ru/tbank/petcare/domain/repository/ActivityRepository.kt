package ru.tbank.petcare.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.tbank.petcare.domain.model.Activity
import ru.tbank.petcare.domain.model.ValidationResult

interface ActivityRepository {

    suspend fun createActivity(petId: String, activity: Activity): ValidationResult<Activity>
    fun getActivitiesByPetId(petId: String): Flow<List<Activity>>
    fun getLastActivitiesByPetId(petId: String, limit: Int): Flow<List<Activity>>
    fun getActivitiesByPeriod(petId: String, startDate: Long, endDate: Long): Flow<List<Activity>>
}
