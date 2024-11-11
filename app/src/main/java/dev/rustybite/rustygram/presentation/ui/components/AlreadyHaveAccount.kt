package dev.rustybite.rustygram.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.ui.theme.baseline
import dev.rustybite.rustygram.presentation.ui.theme.bodyFontFamily

@Composable
fun AlreadyHaveAccount(
    modifier: Modifier,
    onHaveAccountClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.already_have_account),
            style = baseline.bodyLarge.copy(
                fontFamily = bodyFontFamily
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier
                .padding(dimensionResource(id = R.dimen.padding_extra_small))
                .clickable { onHaveAccountClicked() }
        )
    }
}