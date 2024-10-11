package dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.ui.components.RustyPrimaryButton
import dev.rustybite.rustygram.presentation.ui.components.RustySecondaryButton
import dev.rustybite.rustygram.presentation.ui.theme.baseline
import dev.rustybite.rustygram.presentation.ui.theme.bodyFontFamily

@Composable
fun CreateProfilePictureContent(
    uiState: ProfileUiState,
    onAddPictureClicked: () -> Unit,
    onSkipClicked: () -> Unit,
    onViewClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.top_bar_height)))
        Text(
            text = stringResource(id = R.string.create_profile_picture_title),
            style = baseline.headlineMedium.copy(
                fontFamily = bodyFontFamily
            )
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        Text(
            text = stringResource(id = R.string.create_profile_picture_subtitle),
            style = baseline.bodyLarge.copy(
                fontFamily = bodyFontFamily
            )
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_xx_large)))
        ProfileImageView(
            uri = uiState.userProfileUri,
            onViewClicked = { onViewClicked() },
            modifier = modifier
        )
        Spacer(modifier = modifier.weight(1f))
        RustyPrimaryButton(
            text = stringResource(id = R.string.add_picture_btn_text), 
            onClick = onAddPictureClicked,
            loading = uiState.loading
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        RustySecondaryButton(
            text = stringResource(id = R.string.skip_btn_text),
            onClick = onSkipClicked
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_medium)))
    }
}

@Composable
private fun ProfileImageView(
    uri: Uri?,
    onViewClicked: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uri != null) {
            AsyncImage(
                model = ImageRequest.Builder(
                    LocalContext.current
                ).data(uri).build(),
                contentDescription = stringResource(id = R.string.selected_photo_description),
                modifier = modifier
                    .clip(CircleShape)
                    .border(
                        dimensionResource(id = R.dimen.border_width_medium),
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    )
                    .size(dimensionResource(id = R.dimen.profile_image_box_size))
                    .clickable { onViewClicked() },
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = modifier
                    .clip(CircleShape)
                    .border(
                        dimensionResource(id = R.dimen.border_width_medium),
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    )
                    .size(dimensionResource(id = R.dimen.profile_image_box_size))
                    .clickable { onViewClicked() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_filled_icon),
                    contentDescription = stringResource(id = R.string.selected_photo_description),
                    modifier = modifier
                        .size(dimensionResource(id = R.dimen.profile_image_view_size))
                        .align(Alignment.BottomCenter),
                    colorFilter = ColorFilter.tint(
                        MaterialTheme.colorScheme.onBackground.copy(.5f)
                    )
                )
            }
        }
    }
}