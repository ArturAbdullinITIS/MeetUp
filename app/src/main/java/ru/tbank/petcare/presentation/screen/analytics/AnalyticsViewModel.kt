package ru.tbank.petcare.presentation.screen.analytics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tbank.petcare.domain.model.AnalyticsPeriod
import ru.tbank.petcare.domain.usecase.pets.AnalyticsUseCase
import ru.tbank.petcare.domain.usecase.pets.GetLastActivitiesUseCase
import ru.tbank.petcare.domain.usecase.pets.GetLocalPetUseCase
import ru.tbank.petcare.presentation.mapper.toPetCardUIModel

@HiltViewModel(assistedFactory = AnalyticsViewModel.Factory::class)
class AnalyticsViewModel @AssistedInject constructor(
    private val analyticsUseCase: AnalyticsUseCase,
    private val getPetUseCase: GetLocalPetUseCase,
    private val getLastActivitiesUseCase: GetLastActivitiesUseCase,
    @Assisted(PET_ID) private val petId: String
) : ViewModel() {

    companion object {
        private const val PET_ID = "pet_id"
    }

    private val _state = MutableStateFlow(AnalyticsState())
    val state = _state.asStateFlow()

    init {
        loadPet()
        loadAnalytics()
        loadLastActivities()
    }

    fun processCommand(command: AnalyticsCommand) {
        when (command) {
            is AnalyticsCommand.ChangePeriod -> {
                _state.update { state ->
                    state.copy(
                        selectedPeriod = command.period
                    )
                }
                loadAnalytics()
            }
        }
    }

    private fun loadAnalytics() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            val result = analyticsUseCase(
                petId = petId,
                period = _state.value.selectedPeriod
            )
            Log.d(
                "AnalyticsVM",
                "analytics result: success=${result.isSuccess}, error=${result.error}, data=${result.data}"
            )
            if (result.isSuccess && result.data != null) {
                _state.update { state ->
                    state.copy(
                        distanceChart = result.data.distanceChart,
                        expensesChart = result.data.expensesChart,
                        summary = result.data.summary,
                        isLoading = false,
                        errorMessage = null,
                        goalCompletion = result.data.goalCompletion
                    )
                }
            } else {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = result.error?.toString()
                    )
                }
            }
        }
    }

    private fun loadPet() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    isLoading = true
                )
            }
            getPetUseCase(petId)
                .catch {
                    _state.update { state ->
                        state.copy(
                            errorMessage = it.message
                        )
                    }
                }
                .collect { pet ->
                    _state.update { state ->
                        state.copy(pet = pet.toPetCardUIModel())
                    }
                }
            _state.update { state ->
                state.copy(
                    isLoading = false
                )
            }
        }
    }

    private fun loadLastActivities() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            val result = getLastActivitiesUseCase(
                petId = petId,
                limit = 3
            )
            Log.d(
                "AnalyticsVM",
                "last activities: success=${result.isSuccess}, error=${result.error}, size=${result.data?.size}"
            )
            if (result.isSuccess && result.data != null) {
                _state.update { state ->
                    state.copy(
                        lastActivities = result.data,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } else {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = result.error?.toString()
                    )
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(PET_ID) petId: String): AnalyticsViewModel
    }
}

sealed interface AnalyticsCommand {
    data class ChangePeriod(val period: AnalyticsPeriod) : AnalyticsCommand
}
