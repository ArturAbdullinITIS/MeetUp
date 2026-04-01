package ru.tbank.petcare.data.remote.network

import retrofit2.http.GET
import retrofit2.http.Query

interface AnimalsApiService {
    @GET("animals")
    suspend fun getAnimalByName(
        @Query("name") name: String
    ): AnimalResponseDto
}
