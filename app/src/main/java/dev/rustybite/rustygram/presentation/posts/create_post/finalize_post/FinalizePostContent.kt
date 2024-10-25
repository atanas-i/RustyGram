package dev.rustybite.rustygram.presentation.posts.create_post.finalize_post

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.rustybite.rustygram.R

@Composable
fun PostOption(
    option: String,
    leadingIcon: Int,
    trailingIcon: Int,
    audience: @Composable () -> Unit = {},
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        Icon(
            painter = painterResource(leadingIcon),
            contentDescription = stringResource(R.string.location_icon_content_description),
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = modifier
                .size(dimensionResource(R.dimen.icon_size_small))
        )
        Text(
            text = option,
            modifier = modifier
                .weight(1f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))//Arrangement.SpaceBetween
        ) {
            audience()
            Icon(
                painter = painterResource(trailingIcon),
                contentDescription = stringResource(R.string.chevron_forward_content_description),
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = modifier
                    .size(dimensionResource(R.dimen.icon_size_extra_small))

            )
        }

    }
}