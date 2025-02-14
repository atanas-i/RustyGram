package dev.rustybite.rustygram.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.domain.models.Comment
import dev.rustybite.rustygram.domain.models.Profile
import dev.rustybite.rustygram.presentation.ui.theme.RustyGramTheme
import dev.rustybite.rustygram.presentation.ui.theme.baseline
import dev.rustybite.rustygram.presentation.ui.theme.onPrimaryDark
import dev.rustybite.rustygram.presentation.ui.theme.primaryDark
import dev.rustybite.rustygram.presentation.ui.theme.primaryLight

@Composable
fun CommentsModalContent(
    comment: String,
    comments: List<Comment>,
    profile: Profile?,
    loading: Boolean,
    onCommentChange: (String) -> Unit,
    onEmojiClick: () -> Unit,
    onSendComment: () -> Unit,
    onReply: () -> Unit,
    onTranslate: () -> Unit,
    onLikeComment: (Boolean) -> Unit,
    commentLikeCounts: Int,
    isCommentLiked: Boolean,
    isUserSharedStory: Boolean,
    commentingError: String?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.comments),
                style = baseline.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            LazyColumn {
                items(comments) { comment ->
                    CommentItem(
                        profile = profile,
                        comment = comment.comment,
                        commentId = comment.commentId,
                        onReply = onReply,
                        onTranslate = onTranslate,
                        onLikeComment = onLikeComment,
                        commentLikeCounts = commentLikeCounts,
                        commentedAt = "",
                        isCommentLiked = isCommentLiked,
                        isUserSharedStory = isUserSharedStory
                    )
                }
            }
        }
        Box() {
            RustyCommentTextField(
                value = comment,
                onValueChange = onCommentChange,
                onEmojiClick = onEmojiClick,
                onSendClick = onSendComment,
                postHolderUserName = profile?.userName,
                postHolderProfilePicture = profile?.userProfilePicture
            )
        }
    }
}

@Composable
fun CommentItem(
    profile: Profile?,
    comment: String,
    commentId: String,
    onReply: () -> Unit,
    onTranslate: () -> Unit,
    onLikeComment: (Boolean) -> Unit,
    commentLikeCounts: Int,
    commentedAt: String,
    isCommentLiked: Boolean,
    isUserSharedStory: Boolean,
    modifier: Modifier = Modifier
) {
    val painter = if (isCommentLiked) R.drawable.favorite_icon else R.drawable.favorite_outline_icon
    Row(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(profile?.userProfilePicture)
                .placeholder(R.drawable.profile_filled_icon)
                .build(),
            contentDescription = stringResource(R.string.profile_picture),
            modifier = modifier
                .padding(dimensionResource(R.dimen.padding_small))
                .size(dimensionResource(R.dimen.poster_profile_pic_size))
                .clip(CircleShape)
                .border(
                    width = if (isUserSharedStory) dimensionResource(R.dimen.border_width_small)
                    else dimensionResource(R.dimen.no_border_width),
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            primaryLight,
                            primaryDark,
                            onPrimaryDark,

                        )
                    ),
                    shape = CircleShape
                )
        )
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                Text(
                    text = profile?.userName.toString(),
                    style = baseline.bodyLarge.copy(fontWeight = FontWeight.W500),
                    modifier = modifier
                )
                Text(
                    text = commentedAt,
                    style = baseline.bodySmall.copy(fontWeight = FontWeight.W500),
                    modifier = modifier
                )
            }
            Text(
                text = comment,
                style = baseline.bodyMedium,
                modifier = modifier
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_extra_small))
                ) {
                    //IconToggleButton(checked = isCommentLiked, onCheckedChange = onLikeComment) {
                    Box(
                        modifier = modifier
                            .toggleable(
                                value = isCommentLiked,
                                onValueChange = onLikeComment,
                                enabled = true,
                                role = Role.Checkbox,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(
                                    bounded = false,
                                    radius = dimensionResource(R.dimen.ripple_indication_size)
                                )
                            )
                            //.clickable { onLikeComment(!isCommentLiked) }
                    ) {
                        Icon(
                            painter = painterResource(painter),
                            contentDescription = stringResource(R.string.like_content_description),
                            tint = if (isCommentLiked) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground,
                            modifier = modifier
                                .size(dimensionResource(R.dimen.icon_size_XX_small))
                        )
                    }
                    Text(
                        text = commentLikeCounts.toString(),
                        style = baseline.bodySmall
                    )
                }
                Text(
                    text = stringResource(R.string.replay),
                    style = baseline.bodySmall.copy(fontWeight = FontWeight.W500),
                    modifier = modifier
                        .clickable { onReply() }
                )
                Text(
                    text = stringResource(R.string.translate),
                    style = baseline.bodySmall.copy(fontWeight = FontWeight.W500),
                    modifier = modifier
                        .clickable { onTranslate() }
                )
            }
        }
    }
}

@Preview
@Composable
private fun CommentItemPreview() {
    RustyGramTheme {
        var isCommentLiked by remember { mutableStateOf(false) }
        var commentLikeCounts by remember { mutableStateOf(0) }
        Surface {
            CommentItem(
                profile = Profile(
                    birthDate = "", bio = "",fullName = "Atanas Charle", profileId  = "", userProfilePicture = "", userName = "tana", userId = ""
                ),
                comment = "This is what I was talking about, be this creative and you will win",
                commentId = "",
                commentedAt = "3d",
                onReply = {},
                onTranslate = {},
                onLikeComment = { isChecked ->
                    isCommentLiked = isChecked
                    if (isCommentLiked) commentLikeCounts++ else commentLikeCounts--
                                },
                commentLikeCounts = commentLikeCounts,
                isCommentLiked = isCommentLiked,
                isUserSharedStory = true
            )
        }
    }
}