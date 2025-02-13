package dev.rustybite.rustygram.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.ui.theme.baseline

@Composable
fun CommentsModalContent(
    comment: String,
    loading: Boolean,
    onCommentChange: (String) -> Unit,
    onEmojiClick: () -> Unit,
    onSendComment: () -> Unit,
    postHolderUserName: String?,
    postHolderProfilePicture: String?,
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
        }
        Box() {
            RustyCommentTextField(
                value = comment,
                onValueChange = onCommentChange,
                onEmojiClick = onEmojiClick,
                onSendClick = onSendComment,
                postHolderUserName = postHolderUserName,
                postHolderProfilePicture = postHolderProfilePicture
            )
        }
    }
}