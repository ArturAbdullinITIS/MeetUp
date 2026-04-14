package ru.tbank.petcare.domain.model

enum class AnalyticsPeriod {
    WEEK,
    MONTH,
    THREE_MONTHS,
    YEAR
}

data class AnalyticsChartEntry(
    val label: String,
    val value: Float
)

data class AnalyticsSummary(
    val totalWalks: Int = 0,
    val totalExpenses: Float = 0f,
    val avgKm: Float = 0f
)

data class AnalyticsResult(
    val distanceChart: List<AnalyticsChartEntry> = emptyList(),
    val expensesChart: List<AnalyticsChartEntry> = emptyList(),
    val summary: AnalyticsSummary = AnalyticsSummary(),
    val goalCompletion: GoalCompletionSummary = GoalCompletionSummary()
)

data class GoalCompletionSummary(
    val completedGoals: Int = 0,
    val totalGoals: Int = 0,
    val progress: Float = 0f
)
