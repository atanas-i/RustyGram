package dev.rustybite.rustygram.presentation.posts.create_post

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.rustybite.rustygram.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageScreen(
    modifier: Modifier = Modifier,
    viewModel: CreatePostViewModel = hiltViewModel()
) {
    val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            viewModel.onUriChange(uri)
        }
    }
    LaunchedEffect(pickImageLauncher) {
        pickImageLauncher.launch("image/*")
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
        }
    ) { paddingValues ->

    }
}