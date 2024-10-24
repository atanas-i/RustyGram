package dev.rustybite.rustygram.presentation.posts.create_post.image_picker

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.posts.create_post.CreatePostViewModel
import dev.rustybite.rustygram.util.RustyEvents
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageScreen(
    onNavigate: (RustyEvents.Navigate) -> Unit,
    onPopBack: (RustyEvents.PopBackStack) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreatePostViewModel
) {
    val galleryUiState = viewModel.galleryUiState.collectAsStateWithLifecycle().value
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            viewModel.onUriChange(uri)
        }
    }
    LaunchedEffect(viewModel.events) {
        //pickImageLauncher.launch("image/*")
        viewModel.events.collectLatest { events ->
            when(events) {
                is RustyEvents.Navigate -> {
                    onNavigate(events)
                }
                is RustyEvents.PopBackStack -> {
                    onPopBack(events)
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.image_screen_top_bar_title))
                },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.moveBack() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.cancel),
                            contentDescription = stringResource(R.string.cancel_btn),
                            modifier = modifier
                                .size(dimensionResource(R.dimen.icon_size_extra_small))
                        )
                    }
                },
                actions = {
                    Text(
                        text = stringResource(R.string.next),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 18.sp
                        ),
                        modifier = modifier
                            .padding(dimensionResource(R.dimen.padding_small))
                            .clickable { viewModel.moveToEditScreen() }
                    )
                }
            )
        },
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) { paddingValues ->

        Column(
            modifier = modifier
//                .fillMaxSize()
                .consumeWindowInsets(paddingValues)
        ) {
            if (galleryUiState.images.isEmpty()) {
                Text("No images found", style = MaterialTheme.typography.titleLarge)
            } else {
                Column {
                    val selectedUri = uiState.uri ?: galleryUiState.images[0].uri
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(selectedUri)
                            .build(),
                        contentDescription = stringResource(R.string.selected_image),
                        modifier = modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.5f),
                        contentScale = ContentScale.Crop
                    )
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = dimensionResource(R.dimen.padding_medium),
                                vertical = dimensionResource(R.dimen.padding_extra_small)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
                        ) {
                            Text(
                                text = stringResource(R.string.recent),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            )
                            Icon(
                                painter = painterResource(R.drawable.chevron_down_icon),
                                contentDescription = stringResource(R.string.dropdown_icon),
                                modifier = modifier
                                    .size(dimensionResource(R.dimen.icon_size_extra_small))
                            )
                        }
                        Row {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
                            ) {
                                Card(
                                    onClick = {},
                                    shape = CircleShape
                                ) {
                                    Row(
                                        modifier = modifier
                                            .padding(
                                                horizontal = dimensionResource(R.dimen.padding_medium),
                                                vertical = dimensionResource(R.dimen.padding_extra_small)
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.stack_horizontal_icon),
                                            contentDescription = null,
                                            modifier = modifier
                                                .size(dimensionResource(R.dimen.icon_size_extra_small)),
                                            tint = MaterialTheme.colorScheme.onBackground
                                        )
                                        Text(stringResource(R.string.select_multiple))
                                    }
                                }
                            }
                            IconButton(
                                onClick = {  }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.camera_icon),
                                    contentDescription = null,
                                    modifier = modifier
                                        .size(dimensionResource(R.dimen.icon_size_small)),
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
                        items(galleryUiState.images) { image ->
                            Card(
                                onClick = {
                                    viewModel.onUriChange(image.uri)
                                },
                                modifier = modifier
                                    .width(100.dp)
                                    .height(100.dp),
                                shape = RoundedCornerShape(0.dp),
                                border = BorderStroke(
                                    width = (0.4).dp,
                                    color = MaterialTheme.colorScheme.background,
                                )
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(image.uri)
                                        .build(),
                                    contentDescription = image.imageName,
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}