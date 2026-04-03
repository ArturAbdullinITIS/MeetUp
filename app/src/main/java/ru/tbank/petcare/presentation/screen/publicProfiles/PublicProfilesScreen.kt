package ru.tbank.petcare.presentation.screen.publicProfiles

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ru.tbank.petcare.R

@Composable
fun PublicProfilesScreen(
    onPetClick: (String) -> Unit
) {
    PublicProfilesContent(
        onPetClick = onPetClick
    )
}

@Suppress("MagicNumber")
@Composable
private fun PublicProfilesContent(
    onPetClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PublicProfilesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(40.dp)) }
        item {
            Text(
                text = stringResource(R.string.community_feed).uppercase(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.6.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
            )
        }
        item {
            Text(
                text = stringResource(R.string.meet_the_pack),
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }

        items(
            items = state.pets,
            key = { it.id }
        ) { pet ->
            PublicPetCard(
                pet = pet,
                onClick = {
                    onPetClick(pet.id)
                }
            )
        }
    }
}
