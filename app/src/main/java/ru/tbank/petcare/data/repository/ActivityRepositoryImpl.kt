package ru.tbank.petcare.data.repository

import android.util.Log.e
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import ru.tbank.petcare.data.mapper.toDto
import ru.tbank.petcare.di.IoDispatcher
import ru.tbank.petcare.domain.model.Activity
import ru.tbank.petcare.domain.model.ErrorType
import ru.tbank.petcare.domain.model.ValidationResult
import ru.tbank.petcare.domain.repository.ActivityRepository
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) : ActivityRepository {
    private companion object {
        const val COLLECTION_PATH = "activities"
        const val FIELD_PET_ID = "petId"
        const val FIELD_OWNER_ID = "owner_id"
        const val FIELD_DATE = "date"
    }

    private val collection = firestore.collection(COLLECTION_PATH)

    override suspend fun createActivity(
        petId: String,
        activity: Activity
    ): ValidationResult<Activity> = withContext(dispatcherIO) {
        try {
            val currentUserId = firebaseAuth.currentUser?.uid
            if (currentUserId == null) {
                return@withContext ValidationResult(
                    error = ErrorType.AuthValidation()
                )
            }

            val activityToSave = activity.copy(petId = petId, ownerId = currentUserId)
            val docRef = collection.add(activityToSave.toDto()).await()
            val activityId = docRef.id

            val savedActivity = activityToSave.copy(id = activityId)
            ValidationResult(
                data = savedActivity,
                isSuccess = true
            )
        } catch (e: FirebaseFirestoreException) {
            when (e.code) {
                FirebaseFirestoreException.Code.PERMISSION_DENIED ->
                    ValidationResult(
                        error = ErrorType.NetworkError(e.message ?: "")
                    )

                FirebaseFirestoreException.Code.UNAVAILABLE ->
                    ValidationResult(
                        error = ErrorType.NetworkError(e.message ?: "")
                    )

                else -> ValidationResult(
                    error = ErrorType.NetworkError(e.message ?: "")
                )
            }
        } catch (e: FirebaseFirestoreException) {
            ValidationResult(
                error = ErrorType.CommonError(e.message ?: "")
            )
        }
    }

    override fun getActivitiesByPetId(petId: String): Flow<List<Activity>> =
        callbackFlow {
            val listener = collection.whereEqualTo(FIELD_PET_ID, petId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        val activities = snapshot.documents.mapNotNull { doc ->
                            doc.toObject(Activity::class.java)?.copy(id = doc.id)
                        }
                        trySend(activities)
                    }
                }
            awaitClose { listener.remove() }
        }.flowOn(dispatcherIO)
            .catch { e ->
                emit(emptyList())
            }

    override fun getLastActivitiesByPetId(
        petId: String,
        limit: Int
    ): Flow<List<Activity>> = callbackFlow {
        val listener = collection.whereEqualTo(FIELD_PET_ID, petId)
            .orderBy(FIELD_DATE, Query.Direction.DESCENDING)
            .limit(limit.toLong())
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val activities = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Activity::class.java)?.copy(id = doc.id)
                    }
                    trySend(activities)
                }
            }
        awaitClose { listener.remove() }
    }.flowOn(dispatcherIO)
        .catch { e ->
            emit(emptyList())
        }

    override fun getActivitiesByPeriod(
        petId: String,
        startDate: Long,
        endDate: Long
    ): Flow<List<Activity>> = callbackFlow {
        val listener = collection.whereEqualTo(FIELD_PET_ID, petId)
            .whereGreaterThanOrEqualTo(FIELD_DATE, startDate)
            .whereLessThanOrEqualTo(FIELD_DATE, endDate)
            .orderBy(FIELD_DATE)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val activities = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Activity::class.java)?.copy(id = doc.id)
                    }
                    trySend(activities)
                }
            }
        awaitClose { listener.remove() }
    }.flowOn(dispatcherIO)
        .catch { e ->
            emit(emptyList())
        }
}
