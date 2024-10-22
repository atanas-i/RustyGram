package dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.ui.components.AlreadyHaveAccount
import dev.rustybite.rustygram.presentation.ui.components.RustyPrimaryButton
import dev.rustybite.rustygram.presentation.ui.components.RustyTextField
import dev.rustybite.rustygram.presentation.ui.theme.baseline
import dev.rustybite.rustygram.presentation.ui.theme.bodyFontFamily

@Composable
fun CreateFullNameContent(
    uiState: CreateProfileUiState,
    onFullNameChange: (String) -> Unit,
    onNextClicked: () -> Unit,
    onHaveAccountClicked: () -> Unit,
    focusManager: FocusManager,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.top_bar_height)))
        Text(
            text = stringResource(id = R.string.full_name_title),
            style = baseline.headlineMedium.copy(
                fontFamily = bodyFontFamily
            )
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        RustyTextField(
            value = uiState.fullName,
            onValueChange = onFullNameChange,
            label = stringResource(id = R.string.full_name_label),
            placeholder = stringResource(id = R.string.full_name_placeholder),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_large)))
        RustyPrimaryButton(
            text = stringResource(id = R.string.next),
            onClick = { onNextClicked() },
            loading = false
        )
        Spacer(modifier = modifier.weight(1f))
        AlreadyHaveAccount(
            modifier = modifier,
            onHaveAccountClicked = { onHaveAccountClicked() }
        )
    }
}