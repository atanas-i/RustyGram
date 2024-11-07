package dev.rustybite.rustygram.presentation.posts.create_post.finalize_post

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.posts.create_post.CreatePostViewModel
import dev.rustybite.rustygram.presentation.ui.components.RustyPrimaryButton
import dev.rustybite.rustygram.presentation.ui.components.RustyTopAppBar
import dev.rustybite.rustygram.util.RustyEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FinalizePostScreen(
    snackbarHostState: SnackbarHostState,
    isUserCreatingPost: MutableState<Boolean>,
    onNavigate: (RustyEvents.BottomScreenNavigate) -> Unit,
    onPopBack: (RustyEvents.PopBackStack) -> Unit,
    viewModel: CreatePostViewModel,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val state = rememberLazyListState()

    LaunchedEffect(viewModel.events) {
        viewModel.events.collectLatest { events ->
            when (events) {
                is RustyEvents.BottomScreenNavigate -> {
                    onNavigate(events)
                }

                is RustyEvents.PopBackStack -> {
                    onPopBack(events)
                }

                is RustyEvents.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(events.message)
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            RustyTopAppBar(
                title = stringResource(R.string.image_screen_top_bar_title),
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.moveBack() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_arrow_icon),
                            contentDescription = stringResource(R.string.cancel_btn_content_description),
                            modifier = modifier
                                .size(dimensionResource(R.dimen.icon_size_small))
                        )
                    }
                }
            )
        },

        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                state = state,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large))
            ) {
                item {
                    Column(
                        modifier = modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(uiState.uri)
                                .build(),
                            contentDescription = stringResource(R.string.selected_image),

                            )
                    }
                }
                item {
                    TextField(
                        value = uiState.caption,
                        onValueChange = viewModel::onCaptionChange,
                        modifier = modifier
                            .fillMaxWidth(),
                        placeholder = {
                            Text(text = stringResource(R.string.caption_label))
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            focusedIndicatorColor = MaterialTheme.colorScheme.background,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                        )
                    )
                }
                item {
                    Column {
                        HorizontalDivider()
                        Spacer(modifier = modifier.height(dimensionResource(R.dimen.padding_medium)))
                        PostOption(
                            option = stringResource(R.string.add_location),
                            leadingIcon = R.drawable.location_tag_icon,
                            trailingIcon = R.drawable.chevron_forward_icon,
                            onClick = { viewModel.onAddLocation() },
                            modifier = modifier
                        )
                    }
                }
                item {
                    PostOption(
                        option = stringResource(R.string.tag_people),
                        leadingIcon = R.drawable.profile_outlined_icon,
                        trailingIcon = R.drawable.chevron_forward_icon,
                        onClick = { viewModel.onTagPeople() }
                    )
                }
                item {
                    PostOption(
                        option = stringResource(R.string.add_music),
                        leadingIcon = R.drawable.musical_note_icon,
                        trailingIcon = R.drawable.chevron_forward_icon,
                        onClick = { viewModel.addMusic() }
                    )
                }
                item {
                    PostOption(
                        option = stringResource(R.string.audience),
                        leadingIcon = R.drawable.eye_icon,
                        trailingIcon = R.drawable.chevron_forward_icon,
                        onClick = { viewModel.addAudience() },
                        audience = {
                            Text("Everyone")
                        }
                    )
                }
                item {
                    PostOption(
                        option = stringResource(R.string.reminder),
                        leadingIcon = R.drawable.calender_icon,
                        trailingIcon = R.drawable.chevron_forward_icon,
                        onClick = { viewModel.addReminder() },
                    )
                }
                item {
                    Spacer(modifier = modifier.height(40.dp))
                }
            }
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                RustyPrimaryButton(
                    text = stringResource(R.string.share),
                    onClick = {
                        viewModel.createPost()
                        isUserCreatingPost.value = false
                              },
                    loading = false,
                    modifier = modifier
                )
            }
        }
    }
}