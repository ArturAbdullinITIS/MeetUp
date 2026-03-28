package ru.tbank.petcare.presentation.screen.addpet

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ru.tbank.petcare.R
import ru.tbank.petcare.presentation.common.CustomButton


@Composable
fun AddPetScreen(
) {
    AddPetContent()
}

@Composable
fun AddPetContent(
    modifier: Modifier = Modifier,
    viewModel: AddPetViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    Card(
        modifier = modifier
            .scrollable(
                state = scrollState,
                orientation = Orientation.Vertical
            )
            .padding(16.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(48.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AddPetProfilePicture()
            Text(
                text = "Add Profile Photo",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
            SelectableIconStatusRow(
                currentIconsStatus = TODO(),
                onSelectIconStatus = TODO()
            )
            CustomTextField(
                value = state.name,
                onValueChange = TODO(),
                placeholder = TODO(),
                label = stringResource(R.string.pet_name)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomTextField(
                    modifier = Modifier.weight(1f),
                    value = state.breed,
                    onValueChange = TODO(),
                    placeholder = TODO(),
                    label = TODO()
                )
                CustomTextField(
                    modifier = Modifier.weight(1f),
                    value = state.weight,
                    onValueChange = TODO(),
                    placeholder = TODO(),
                    label = TODO()
                )
            }
            CustomTextField(
                value = state.dateOfBirth,
                onValueChange = TODO(),
                placeholder = TODO(),
                label = stringResource(R.string.pet_name)
            )
            CustomSegmentedControlButton(
                selectedIndex = TODO(),
                onSelected = TODO()
            )
            CustomTextField(
                value = state.notes,
                onValueChange = TODO(),
                placeholder = TODO(),
                label = TODO()
            )
            PublicProfileCardSwitch(
                onCheckedChanged = TODO()
            )
            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = TODO(),
                content = TODO(),
                text = TODO()
            )
        }
    }

}