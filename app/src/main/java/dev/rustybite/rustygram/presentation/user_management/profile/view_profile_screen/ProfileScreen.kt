package dev.rustybite.rustygram.presentation.user_management.profile.view_profile_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.ui.theme.baseline
import dev.rustybite.rustygram.util.RustyEvents
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { events ->
            when (events) {
                is RustyEvents.ShowSnackBar -> {}
                else -> Unit
            }
        }
    }

    uiState.profile?.let { profile ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = modifier
                                .clickable { },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
                        ) {
                            Text(
                                text = profile.userName,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.chevron_down_icon),
                                contentDescription = stringResource(R.string.dropdown_icon),
                                modifier = modifier.size(dimensionResource(R.dimen.icon_size_small))
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { /*TODO*/ },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.threads_outline_icon),
                                contentDescription = stringResource(R.string.thread_icon),
                                modifier = modifier.size(dimensionResource(R.dimen.icon_size_small))
                            )
                        }
                        IconButton(
                            onClick = { /*TODO*/ },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_outline_icon),
                                contentDescription = stringResource(R.string.add_icon_description),
                                modifier = modifier.size(dimensionResource(R.dimen.icon_size_small))
                            )
                        }
                        IconButton(
                            onClick = { /*TODO*/ },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.menu_icon),
                                contentDescription = stringResource(R.string.menu_icon_description),
                                modifier = modifier.size(dimensionResource(R.dimen.icon_size_small))
                            )
                        }
                    },
                )
            },
            modifier = modifier
                .fillMaxSize()
                .consumeWindowInsets(WindowInsets.safeDrawing)
        ) { paddingValues ->
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .consumeWindowInsets(paddingValues),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                //Spacer(modifier = modifier.height(80.dp))
                item {
                    Row(
                        modifier = modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
                    ) {
                        AsyncImage(
                            ImageRequest.Builder(LocalContext.current)
                                .data(profile.userProfilePicture)
                                .build(),
                            contentDescription = profile.userProfilePicture,
                            modifier = modifier
                                .size(dimensionResource(R.dimen.profile_picture_size))
                                .border(
                                    width = dimensionResource(R.dimen.border_radius_small),
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                )
                                .clip(CircleShape)
                        )
                        Row(
                            modifier = modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = (0).toString(),
                                    style = baseline.titleLarge.copy(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(text = "Posts")
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = (0).toString(),
                                    style = baseline.titleLarge.copy(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(text = "Followers")
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = (0).toString(),
                                    style = baseline.titleLarge.copy(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(text = "Following")
                            }
                        }
                    }
                }
                //Spacer(modifier = modifier.height(dimensionResource(R.dimen.padding_medium)))
                item {
                    Column {
                        Text(
                            text = profile.fullName,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = modifier.height(dimensionResource(R.dimen.padding_small)))
                        Column {
                            Text(text = "Mobile Developers")
                            Text(text = "Android")
                            Text(text = "Kotlin")
                        }
                    }
                }
                //Spacer(modifier = modifier.height(dimensionResource(R.dimen.padding_medium)))
                item {
                    Card(
                        modifier = modifier
                            .fillMaxWidth(),

                        ) {
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = dimensionResource(R.dimen.padding_medium),
                                    vertical = dimensionResource(R.dimen.padding_small),
                                ),
                        ) {
                            Text(
                                "Professional dashboard",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text("0 views in the last 0 days")
                        }
                    }
                    Spacer(modifier.height(dimensionResource(R.dimen.padding_small)))
                    Row(
                        modifier = modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly//spacedBy(dimensionResource(R.dimen.padding_medium))
                    ) {
                        ProfileOptionsButton(
                            text = stringResource(R.string.edit_profile),
                            modifier = modifier
                        )
                        ProfileOptionsButton(
                            text = stringResource(R.string.share_profile),
                            modifier = modifier
                        )
                    }
                }
                //Spacer(modifier = modifier.height(dimensionResource(R.dimen.padding_medium)))
            }
        }
    }
}

@Composable
private fun ProfileOptionsButton(
    text: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(
                //horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_medium),
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = modifier
                .padding(end = dimensionResource(R.dimen.padding_medium))
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
    }
}