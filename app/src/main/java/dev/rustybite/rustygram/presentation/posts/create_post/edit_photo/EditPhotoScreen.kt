package dev.rustybite.rustygram.presentation.posts.create_post.edit_photo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.posts.create_post.CreatePostViewModel
import dev.rustybite.rustygram.presentation.ui.components.RustyTopAppBar
import dev.rustybite.rustygram.util.RustyEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditPhotoScreen(
    snackBarHostState: SnackbarHostState,
    onNavigate: (RustyEvents.Navigate) -> Unit,
    onPopBack: (RustyEvents.PopBackStack) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreatePostViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(viewModel.events) {
        viewModel.events.collectLatest { events ->
            when (events) {
                is RustyEvents.Navigate -> {
                    onNavigate(events)
                }

                is RustyEvents.PopBackStack -> {
                    onPopBack(events)
                }

                is RustyEvents.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(events.message)
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            RustyTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {viewModel.moveBack() }) {
                        Icon(
                            painter = painterResource(R.drawable.cancel),
                            contentDescription = stringResource(R.string.cancel_btn_content_description),
                            modifier = modifier
                                .size(dimensionResource(R.dimen.icon_size_extra_small))
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.goToEdit()
                    }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.tool_icon),
                            contentDescription = stringResource(R.string.cancel_btn_content_description),
                            modifier = modifier
                                .size(dimensionResource(R.dimen.icon_size_small)),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.musical_note_icon),
                            contentDescription = stringResource(R.string.cancel_btn_content_description),
                            modifier = modifier
                                .size(dimensionResource(R.dimen.icon_size_small)),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                modifier = modifier,
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) { paddingValues ->
        Column(
            modifier = modifier
                .consumeWindowInsets(paddingValues)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uiState.uri)
                    .build(),
                contentDescription = stringResource(R.string.selected_image),
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.6f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = modifier.height(dimensionResource(R.dimen.spacer_medium)))
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                uiState.uri?.let { FilterEffects(it) }
                Spacer(modifier = modifier.height(24.dp))
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Card(
                        onClick = {
                            viewModel.goToEdit()
                        },
                        shape = CircleShape
                    ) {
                        Text(
                            "Edit",
                            modifier = modifier
                                .padding(
                                    horizontal = dimensionResource(R.dimen.padding_medium),
                                    vertical = dimensionResource(R.dimen.padding_small),
                                )
                        )
                    }
                    Card(
                        onClick = {
                            viewModel.goToFinalizePost()
                        },
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            "Next",
                            modifier = modifier
                                .padding(
                                    horizontal = dimensionResource(R.dimen.padding_medium),
                                    vertical = dimensionResource(R.dimen.padding_small),
                                )
                        )
                    }
                }
            }
        }
    }
}