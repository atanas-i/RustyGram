package dev.rustybite.rustygram.presentation.posts.fetch_posts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.rustybite.rustygram.util.RustyEvents

@Composable
fun FeedsScreen(
    modifier: Modifier = Modifier,
    viewModel: GetPostsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is RustyEvents.ShowSnackBar -> {}
                else -> {}
            }
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            FeedsContent(
                uiState = uiState,
                onCommentClicked = {},
                onShareClicked = { viewModel.onShareClicked() },
                onLikeClicked = viewModel::onLikeClicked,
                onBookmarkClicked = viewModel::onBookmarkClicked,
                onOptionClicked = {},
                loading = uiState.loading,
                modifier = modifier
                    .fillMaxSize()
            )
        }
    }
}