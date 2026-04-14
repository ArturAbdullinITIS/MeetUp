package ru.tbank.petcare.presentation.screen.analytics

import ru.tbank.petcare.domain.model.Activity
import ru.tbank.petcare.domain.model.AnalyticsChartEntry
import ru.tbank.petcare.domain.model.AnalyticsPeriod
import ru.tbank.petcare.domain.model.AnalyticsSummary
import ru.tbank.petcare.domain.model.GoalCompletionSummary
import ru.tbank.petcare.presentation.model.PetCardUIModel

data class AnalyticsState(
    val selectedPeriod: AnalyticsPeriod = AnalyticsPeriod.WEEK,
    val distanceChart: List<AnalyticsChartEntry> = emptyList(),
    val expensesChart: List<AnalyticsChartEntry> = emptyList(),
    val summary: AnalyticsSummary = AnalyticsSummary(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val pet: PetCardUIModel = PetCardUIModel(),
    val lastActivities: List<Activity> = emptyList(),
    val goalCompletion: GoalCompletionSummary = GoalCompletionSummary()
)
