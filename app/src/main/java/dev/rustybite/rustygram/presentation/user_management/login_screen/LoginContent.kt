package dev.rustybite.rustygram.presentation.user_management.login_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.ui.components.RustyBiteLogo
import dev.rustybite.rustygram.presentation.ui.components.RustyGramLogo
import dev.rustybite.rustygram.presentation.ui.components.RustyPrimaryButton
import dev.rustybite.rustygram.presentation.ui.components.RustySecondaryButton
import dev.rustybite.rustygram.presentation.ui.components.RustyTextField

@Composable
fun LoginContent(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onSignUpClicked: () -> Unit,
    onForgotPassword: () -> Unit,
    onOpenLanguageSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = modifier.weight(.1f))
        Text(
            text = uiState.selectedOption,
            modifier = modifier
                .clickable { onOpenLanguageSelection() }
        )
        Spacer(modifier = modifier.weight(.2f))
        RustyGramLogo()
        Spacer(modifier = modifier.weight(.3f))
        RustyTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            label = stringResource(id = R.string.email_label),
            placeholder = stringResource(id = R.string.email_placeholder)
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_large)))
        RustyTextField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            label = stringResource(id = R.string.password_label),
            placeholder = stringResource(id = R.string.password_placeholder)
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_large)))
        RustyPrimaryButton(
            text = stringResource(id = R.string.login),
            onClick = onLogin,
            loading = uiState.loading
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_large)))
        RustySecondaryButton(
            text = stringResource(id = R.string.forgot_password),
            onClick = onForgotPassword
        )
        Spacer(modifier = modifier.weight(1f))
        RustySecondaryButton(
            text = stringResource(id = R.string.create_new_account),
            onClick = onSignUpClicked,
            borderColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_small)))
        RustyBiteLogo(logoColor = MaterialTheme.colorScheme.onBackground)
    }
}