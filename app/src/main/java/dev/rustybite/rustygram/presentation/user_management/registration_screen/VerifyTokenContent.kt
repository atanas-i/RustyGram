package dev.rustybite.rustygram.presentation.user_management.registration_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.ui.components.RustyPrimaryButton
import dev.rustybite.rustygram.presentation.ui.components.RustySecondaryButton
import dev.rustybite.rustygram.presentation.ui.components.RustyTextField
import dev.rustybite.rustygram.presentation.ui.theme.baseline
import dev.rustybite.rustygram.presentation.ui.theme.bodyFontFamily

@Composable
fun VerifyTokenContent(
    uiState: RegistrationUiState,
    onTokenChange: (String) -> Unit,
    onSubmitToken: () -> Unit,
    onResendToken: () -> Unit,
    onHaveAccountClicked: () -> Unit,
    focusManager: FocusManager,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(id = R.string.otp_screen_title),
            style = baseline.headlineLarge.copy(
                fontFamily = bodyFontFamily
            )
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_large)))
        Text(
            text = "${stringResource(id = R.string.otp_screen_subtitle)} ${uiState.email}.",
            style = baseline.bodyLarge.copy(
                fontFamily = bodyFontFamily
            ),
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        RustyTextField(
            value = uiState.token,
            onValueChange = onTokenChange,
            label = stringResource(id = R.string.confirmations_label),
            placeholder = stringResource(id = R.string.confirmation_placeholder),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            modifier = modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.padding_small)
                )

        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        RustyPrimaryButton(
            text = stringResource(id = R.string.submit_button),
            loading = uiState.loading,
            onClick = onSubmitToken,
            modifier = modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.padding_extra_small)
                )
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        RustySecondaryButton(
            text = stringResource(id = R.string.resend_otp_button),
            onClick = onResendToken,
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
