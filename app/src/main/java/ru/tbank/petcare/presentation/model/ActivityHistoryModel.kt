package ru.tbank.petcare.presentation.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import ru.tbank.petcare.domain.model.ActivityDetails
import ru.tbank.petcare.domain.model.ActivityType
import java.util.Date

data class ActivityHistoryModel(
    val activityType: ActivityType = ActivityType.WALK,
    val activityDate: Date? = null,
    val notes: String = "",
    val details: ActivityDetails = ActivityDetails.Walk(),
    val iconTint: Color,
    val bgColor: Color,
    val iconVector: ImageVector,
    val trailingText: String
)
