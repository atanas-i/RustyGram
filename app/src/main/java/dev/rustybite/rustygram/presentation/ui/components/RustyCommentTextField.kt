package dev.rustybite.rustygram.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.ui.theme.RustyGramTheme

@Composable
fun RustyCommentTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onEmojiClick: () -> Unit,
    postHolderUserName: String? = "",
    postHolderProfilePicture: String? = "",
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    val colors = TextFieldDefaults.colors(
        focusedTextColor = contentColor,
        unfocusedTextColor = contentColor,
        unfocusedContainerColor = backgroundColor,
        focusedContainerColor = backgroundColor,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    )

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth(),
        colors = colors,
        placeholder = {
            Text(
                text = "${stringResource(R.string.comment_text_field_placeholder)} $postHolderUserName..."
            )
        },
        leadingIcon = {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(postHolderProfilePicture)
                    .placeholder(R.drawable.profile_filled_icon)
                    .build(),
                contentDescription = stringResource(R.string.profile_picture),
                modifier = Modifier
                    .size(dimensionResource(R.dimen.comment_section_profile_picture_size))
                    .clip(CircleShape),
                colorFilter = ColorFilter.tint(contentColor)
            )
        },
        trailingIcon = {
            val colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
            if (value.isEmpty()) {
                IconButton(
                    onClick = onEmojiClick,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.happy_face_emoji_icon),
                        contentDescription = stringResource(R.string.emoji_icon),
                        modifier = modifier
                            .size(dimensionResource(R.dimen.icon_size_small))
                    )
                }
//            } else {
//                Button(
//                    onSendClick,
//                    modifier = modifier
//                        .width(32.dp)
//                        //.clickable { onSendClick() }
//                        .background(MaterialTheme.colorScheme.primary),
//                    //contentAlignment = Alignment.Center
//                ) {
//                    Icon(
//                        painter = painterResource(R.drawable.up_arrow_icon),
//                        contentDescription = stringResource(R.string.up_arrow_icon),
//                        modifier = modifier
//                            .size(dimensionResource(R.dimen.icon_size_small))
//                    )
//                }
            } else {
                IconButton(
                    onClick = onSendClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.up_arrow_icon),
                        contentDescription = stringResource(R.string.up_arrow_icon),
                        modifier = modifier
                            .size(dimensionResource(R.dimen.icon_size_small))
                    )
                }
            }
        }
    )
}


@Preview
@Composable
private fun RustyCommentTextFieldPreview() {
    RustyGramTheme {
        Surface() {
            RustyCommentTextField(
                value = "Hello",
                onValueChange = {},
                onSendClick = {},
                onEmojiClick = {}
            )
        }
    }
}