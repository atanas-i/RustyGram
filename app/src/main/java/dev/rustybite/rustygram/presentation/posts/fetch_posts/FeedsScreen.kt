package dev.rustybite.rustygram.presentation.posts.fetch_posts

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsIgnoringVisibility
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.domain.models.Profile
import dev.rustybite.rustygram.presentation.ui.components.CommentsModalContent
import dev.rustybite.rustygram.presentation.ui.components.RustyCommentTextField
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
            if (uiState.isCommentClicked) {
                ModalBottomSheet(
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                    containerColor = MaterialTheme.colorScheme.background,
                    onDismissRequest = { viewModel.onCommentClicked(false) },
                ) {
                    CommentsModalContent(
                        comment = uiState.comment,
                        loading = uiState.loading,
                        comments = uiState.comments,
                        profile = profile,
                        onCommentChange = viewModel::onCommentChange,
                        onEmojiClick = { viewModel.onEmojiClick() },
                        onReply = {},
                        onTranslate = {},
                        onLikeComment = {},
                        commentLikeCounts = uiState.commentLikeCounts,
                        isCommentLiked = uiState.isLiked,
                        isUserSharedStory = false,
                        onSendComment = {
                            viewModel.onSendComment(
                                uiState.comment,
                                uiState.postId,
                                profile?.profileId.toString(),
                            )
                            viewModel.onCommentChange("")
                        },
                        commentingError = uiState.commentingError
                    )
                }
            }
            FeedsContent(
                uiState = uiState,
                profile = profile,
                userId = userId,
                onCommentClicked = { viewModel.onCommentClicked(true) },
                onShareClicked = { viewModel.onShareClicked() },
                onLikeClicked = viewModel::onLikeClicked,
                onBookmarkClicked = viewModel::onBookmarkClicked,
                onOptionClicked = {},
                loading = uiState.loading,
                modifier = modifier,
                onPostIdCaptured = viewModel::onPostIdCaptured
                //.fillMaxSize()
            )
        }
    }
}
