package dev.rustybite.rustygram.presentation.posts.fetch_posts

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.domain.models.Bookmark
import dev.rustybite.rustygram.domain.models.Like
import dev.rustybite.rustygram.domain.models.Post
import java.util.UUID

@Composable
fun FeedsContent(
    uiState: FetchPostsUiState,
    onCommentClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onLikeClicked: (Like) -> Unit,
    onBookmarkClicked: (Bookmark) -> Unit,
    onOptionClicked: () -> Unit,
    loading: Boolean,
    modifier: Modifier = Modifier
) {
    var feeds by remember { mutableStateOf(uiState.feeds) }
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(uiState.feeds) { index, post ->
            PostItem(
                post = post,
                uiState = uiState,
                onCommentClicked = onCommentClicked,
                onShareClicked = onShareClicked,
                onLikeClicked = {
                    val like = Like(
                        likeId = UUID.randomUUID().toString(),
                        postId = post.postId,
                        userId = post.profileId,
                    )
                    onLikeClicked(like)
                },
                onBookmarkClicked = {
                    val bookmark = Bookmark(
                        bookmarkId = UUID.randomUUID().toString(),
                        postId = post.postId,
                        userId = post.profileId,
                    )
                    onBookmarkClicked(bookmark)
                },
                onOptionClicked = onOptionClicked,
                loading = loading
            )
        }
        item {
            Spacer(
                modifier = modifier
            )
        }
    }
}

@Composable
fun PostItem(
    post: Post,
    uiState: FetchPostsUiState,
    onCommentClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onLikeClicked: () -> Unit,
    onBookmarkClicked: () -> Unit,
    onOptionClicked: () -> Unit,
    loading: Boolean,
    modifier: Modifier = Modifier
) {
    Column {
        PostHeader(
            post = post,
            onOptionClicked = onOptionClicked,
            posterProfilePic = "",
            posterUserName = "",
            loading = loading,
            context = LocalContext.current,
        )
        AsyncImage(
            model = ImageRequest.Builder(
                LocalContext.current
            )
                .data(post.photoUrl)
                .crossfade(loading)
                .placeholder(R.drawable.profile_outlined_icon)
                .build(),
            contentDescription = stringResource(R.string.post_image),
            contentScale = ContentScale.FillWidth,
            modifier = modifier
                .background(MaterialTheme.colorScheme.background.copy(.3f))
                .fillMaxWidth(),
        )
        PostFooter(
            uiState = uiState,
            onLikeClicked = onLikeClicked,
            onCommentClicked = onCommentClicked,
            onShareClicked = onShareClicked,
            onBookmarkClicked = onBookmarkClicked,
        )
    }
}

@Composable
fun PostHeader(
    post: Post,
    onOptionClicked: () -> Unit,
    posterProfilePic: String,
    posterUserName: String,
    loading: Boolean,
    context: Context,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(R.drawable.profile_outlined_icon)
                .crossfade(loading)
                .placeholder(R.drawable.profile_outlined_icon)
                .build(),
            contentDescription = stringResource(R.string.poster_profile_pic),
            modifier = modifier
                .size(dimensionResource(R.dimen.poster_profile_pic_size))
                .clip(CircleShape)
                .border(
                    width = dimensionResource(R.dimen.border_width_medium),
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            //contentScale = ContentScale.Fit
        )
        Text(
            text = posterUserName,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.W500,
            )
        )
        Spacer(modifier = modifier.weight(1f))
        IconButton(onClick = onOptionClicked) {
            Icon(
                painter = painterResource(R.drawable.more_vert_icon),
                contentDescription = stringResource(R.string.option_more_content_description),
                modifier = modifier
                    .size(dimensionResource(R.dimen.icon_size_extra_small))
            )
        }
    }
}

@Composable
fun PostFooter(
    uiState: FetchPostsUiState,
    onLikeClicked: () -> Unit,
    onCommentClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onBookmarkClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isBookmarked by remember { mutableStateOf(false) }
    var isLiked by remember { mutableStateOf(false) }
    Row {
        Row {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                IconButton(
                    onClick = {
                        isLiked = !isLiked
                        onLikeClicked()
                    }
                ) {
                    val likeIcon =
                        if (isLiked) R.drawable.favorite_icon else R.drawable.favorite_outline_icon
                    Icon(
                        painter = painterResource(likeIcon),
                        contentDescription = stringResource(R.string.like_content_description),
                        modifier = modifier
                            .size(dimensionResource(R.dimen.icon_size_small)),
                        tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = uiState.likesCount.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.W300,
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                IconButton(onClick = onCommentClicked) {
                    Icon(
                        painter = painterResource(R.drawable.text_msg_icon),
                        contentDescription = stringResource(R.string.like_content_description),
                        modifier = modifier
                            .size(dimensionResource(R.dimen.icon_size_small)),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = uiState.commentsCount.toString(),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.W300,
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                IconButton(onClick = onShareClicked) {
                    Icon(
                        painter = painterResource(R.drawable.send_bend_icon),
                        contentDescription = stringResource(R.string.like_content_description),
                        modifier = modifier
                            .size(dimensionResource(R.dimen.icon_size_small)),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = uiState.commentsCount.toString(),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.W300,
                    )
                )
            }
        }
        Spacer(modifier = modifier.weight(1f))
        IconButton(onClick = {
            isBookmarked = !isBookmarked
            onBookmarkClicked()
            //bookmarkedState.value = !bookmarkedState.value
        }
        ) {
            val bookmarkIcon =
                if (isBookmarked) R.drawable.bookmark_icon else R.drawable.bookmark_outline_icon
            Icon(
                painter = painterResource(bookmarkIcon),
                contentDescription = stringResource(R.string.like_content_description),
                modifier = modifier
                    .size(dimensionResource(R.dimen.icon_size_small)),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}