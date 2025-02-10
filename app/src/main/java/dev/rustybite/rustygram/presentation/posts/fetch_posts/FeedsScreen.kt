package dev.rustybite.rustygram.presentation.posts.fetch_posts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsIgnoringVisibility
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.rustybite.rustygram.domain.models.Profile
import dev.rustybite.rustygram.util.RustyEvents

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeedsScreen(
    snackBarHostState: SnackbarHostState,
    profile: Profile?,
    userId: String,
    modifier: Modifier = Modifier,
    viewModel: GetPostsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is RustyEvents.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBarsIgnoringVisibility)
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            FeedsContent(
                uiState = uiState,
                profile = profile,
                userId = userId,
                onCommentClicked = {},
                onShareClicked = { viewModel.onShareClicked() },
                onLikeClicked = viewModel::onLikeClicked,
                onBookmarkClicked = viewModel::onBookmarkClicked,
                onOptionClicked = {},
                loading = uiState.loading,
                modifier = modifier
                    //.fillMaxSize()
            )
        }
    }
}