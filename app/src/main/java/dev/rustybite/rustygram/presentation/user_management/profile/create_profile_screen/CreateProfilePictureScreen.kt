package dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.ui.theme.baseline
import dev.rustybite.rustygram.presentation.ui.theme.bodyFontFamily
import dev.rustybite.rustygram.util.RustyEvents
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProfilePictureScreen(
    onNavigate: (RustyEvents.Navigate) -> Unit,
    onPopBackStack: (RustyEvents.PopBackStack) -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val appEvents = viewModel.event
    var isImageViewClicked by remember { mutableStateOf(false) }
    val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            viewModel.onProfileUriChange(uri)
        }
    }

    LaunchedEffect(appEvents) {
        appEvents.collectLatest { event ->
            when(event) {
                is RustyEvents.Navigate -> onNavigate(event)
                is RustyEvents.PopBackStack -> onPopBackStack(event)
                is RustyEvents.ShowSnackBar -> Unit
                is RustyEvents.ShowToast -> Unit
            }
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .consumeWindowInsets(paddingValues)
        ) {
            if (isImageViewClicked) {
                ModalBottomSheet(
                    onDismissRequest = { isImageViewClicked = false},
                    sheetState = sheetState
                ) {
                    AddPictureActionContent(
                        onChooseFromGalleryClicked = {
                            pickImageLauncher.launch("image/*")
                            isImageViewClicked = false
                        },
                        onTakePictureClicked = {},
                        modifier = modifier
                    )
                }
            }
            CreateProfilePictureContent(
                uiState = uiState,
                onAddPictureClicked = viewModel::onAddPictureClicked,
                onSkipClicked = viewModel::onSkipClicked,
                onViewClicked = { isImageViewClicked = true },
            )
        }
    }
}

@Composable
private fun AddPictureActionContent(
    onChooseFromGalleryClicked: () -> Unit,
    onTakePictureClicked: () -> Unit,
    modifier: Modifier,
    cardContentColor: Color = MaterialTheme.colorScheme.onBackground,
    cardContainerColor: Color = CardDefaults.cardColors().containerColor//MaterialTheme.colorScheme.background
) {
    val color = CardDefaults.cardColors(
        containerColor = cardContainerColor.copy(.5f),
        contentColor = cardContentColor
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                dimensionResource(id = R.dimen.padding_large),
            )
    ) {
        Text(
            text = stringResource(id = R.string.modal_add_picture_title),
            style = baseline.headlineMedium.copy(
                fontFamily = bodyFontFamily
            )
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_large)))
        Card(
            colors = color
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.choose_from_gallery),
                    style = baseline.bodyLarge.copy(
                        fontFamily = bodyFontFamily
                    ),
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable { onChooseFromGalleryClicked() }
                        .padding(
                            horizontal = dimensionResource(id = R.dimen.padding_medium),
                            vertical = dimensionResource(id = R.dimen.padding_small)
                        )
                )
                Text(
                    text = stringResource(id = R.string.take_picture),
                    style = baseline.bodyLarge.copy(
                        fontFamily = bodyFontFamily
                    ),
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable { onTakePictureClicked() }
                        .padding(
                            horizontal = dimensionResource(id = R.dimen.padding_medium),
                            vertical = dimensionResource(id = R.dimen.padding_small)
                        )
                )
            }
        }
        Spacer(modifier = modifier.weight(1f))
    }
}