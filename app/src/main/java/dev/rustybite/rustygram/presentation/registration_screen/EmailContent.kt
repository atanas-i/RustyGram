package dev.rustybite.rustygram.presentation.registration_screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.ui.components.RustyPrimaryButton
import dev.rustybite.rustygram.presentation.ui.components.RustySecondaryButton
import dev.rustybite.rustygram.presentation.ui.components.RustyTextField
import dev.rustybite.rustygram.presentation.ui.theme.RustyGramTheme
import dev.rustybite.rustygram.presentation.ui.theme.baseline
import dev.rustybite.rustygram.presentation.ui.theme.bodyFontFamily

@Composable
fun EmailContent(
    uiState: RegistrationUiState,
    onEmailChange: (String) -> Unit,
    onSubmitEmail: () -> Unit,
    onHaveAccountClicked: () -> Unit,
    onSignUpWithPhone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(id = R.string.email_screen_title),
            style = baseline.headlineLarge.copy(
                fontFamily = bodyFontFamily
            )
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_large)))
        Text(
            text = stringResource(id = R.string.registration_screen_subtitle),
            style = baseline.bodyLarge.copy(
                fontFamily = bodyFontFamily
            ),
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        RustyTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            label = stringResource(id = R.string.email_label),
            placeholder = stringResource(id = R.string.email_placeholder),
            modifier = modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.padding_small)
                )

        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        RustyPrimaryButton(
            text = stringResource(id = R.string.button_next),
            onClick = onSubmitEmail,
            loading = uiState.loading,
            modifier = modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.padding_extra_small)
                )
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_extra_small)))
        RustySecondaryButton(
            text = stringResource(id = R.string.sign_up_with_phone_button),
            onClick = onSignUpWithPhone,
            modifier = modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.padding_extra_small)
                )

        )
        Spacer(modifier = modifier.weight(1f))
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
}