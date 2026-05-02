package ru.tbank.petcare.presentation.screen.allRecentActivities

import ru.tbank.petcare.domain.model.Activity
import ru.tbank.petcare.domain.model.ActivityType
import ru.tbank.petcare.presentation.model.ActivityHistoryFilterOption

data class AllRecentActivitiesState(
    val activities: List<Activity> = emptyList(),
    val filterOption: ActivityHistoryFilterOption = ActivityHistoryFilterOption.ALL,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val filteredActivities: List<Activity>
        get() = when (filterOption) {
            ActivityHistoryFilterOption.GROOMING -> activities.filter { it.activityType == ActivityType.GROOMING }
            ActivityHistoryFilterOption.WALK -> activities.filter { it.activityType == ActivityType.WALK }
            ActivityHistoryFilterOption.VET -> activities.filter { it.activityType == ActivityType.VET }
            ActivityHistoryFilterOption.ALL -> activities
        }
}
