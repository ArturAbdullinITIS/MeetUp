package ru.tbank.petcare.domain.repository

import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.Flow

interface PetsRepository {

    suspend fun getAllUsersPets(user: User): Flow<List<Pet>>
}