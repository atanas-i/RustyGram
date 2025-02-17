package dev.rustybite.rustygram.presentation.posts.fetch_posts

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsIgnoringVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.rustybite.rustygram.domain.models.Profile
import dev.rustybite.rustygram.util.RustyEvents
import dev.rustybite.rustygram.util.TAG

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
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
                    Log.d(TAG, "FeedsScreen: Showing snackbar")
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
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                onCommentClicked = { postId ->
                    viewModel.getPostComments(postId)
                    viewModel.onCommentClicked(true)
                },
                onShareClicked = { viewModel.onShareClicked() },
                onLikeClicked = viewModel::onLikeClicked,
                onBookmarkClicked = viewModel::onBookmarkClicked,
                onOptionClicked = {},
                onCommenting = { postId ->
                    viewModel.onSendComment(
                        comment = uiState.comment,
                        postId = postId,
                        userId = profile?.profileId.toString(),
                    )
                    viewModel.onCommentChange("")
                },
                onReplyingComment = { viewModel.onReplyingComment() },
                onTranslatingComment = { viewModel.onTranslatingComment() },
                onLikingComment = { viewModel.onLikingComment(it) },
                onEmojiClick = { viewModel.onEmojiClick() },
                onSheetDismiss = { viewModel.onCommentClicked(false) },
                onCommentChange = viewModel::onCommentChange,
                onCommentingErrorChange = viewModel::onCommentingErrorChange,
                loading = uiState.loading,
                modifier = modifier,
            )
        }
    }
}
