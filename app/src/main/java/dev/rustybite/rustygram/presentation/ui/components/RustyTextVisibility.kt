package dev.rustybite.rustygram.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.rustybite.rustygram.R

@Composable
fun RustyTextVisibility(
    isTextVisible: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val resId = if (isTextVisible) R.drawable.hide_password_solid_icon else R.drawable.show_password_solid_icon
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(id = resId),
            contentDescription = stringResource(id = R.string.show_password_content_description),
            modifier = modifier
                .size(dimensionResource(id = R.dimen.visibility_icon_size))
        )
    }
}