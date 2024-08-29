package dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.ui.components.AlreadyHaveAccount
import dev.rustybite.rustygram.presentation.ui.components.RustyDatePicker
import dev.rustybite.rustygram.presentation.ui.components.RustyPrimaryButton
import dev.rustybite.rustygram.presentation.ui.theme.baseline
import dev.rustybite.rustygram.presentation.ui.theme.bodyFontFamily
import dev.rustybite.rustygram.util.convertMillisToDate
import java.time.LocalDateTime
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDayContent(
    uiState: ProfileUiState,
    onBirthDateChange: (Long?) -> Unit,
    onNextClicked: () -> Unit,
    onHaveAccountClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = uiState.birthDate)
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val formatedDate = uiState.birthDate?.let { millis ->
        convertMillisToDate(millis)
    } ?: ""

    if (isDatePickerVisible) {
        RustyDatePicker(
            onDateSelected = { onBirthDateChange(datePickerState.selectedDateMillis) },
            onDismiss = { isDatePickerVisible = false },
            datePickerState = datePickerState
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.top_bar_height)))
        Text(
            text = stringResource(id = R.string.birth_date_screen_title),
            style = baseline.headlineLarge.copy(
                fontFamily = bodyFontFamily
            )
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_large)))
        Text(
            text = stringResource(id = R.string.birth_date_screen_subtitle),
            style = baseline.bodyLarge.copy(
                fontFamily = bodyFontFamily
            )
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_medium)))
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.tf_radius_medium)))
                .border(
                    width = dimensionResource(id = R.dimen.tf_border_width_small),
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.tf_radius_medium))
                )
                .clickable { isDatePickerVisible = !isDatePickerVisible },
            colors = CardDefaults.cardColors(
                contentColor = MaterialTheme.colorScheme.onBackground,
                containerColor = Color.Transparent
            )
        ) {
            Column(
                modifier = modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                Text(
                    text = stringResource(id = R.string.birth_date_label),
                    style = baseline.bodySmall.copy(
                        fontFamily = bodyFontFamily
                    )
                )
                Text(
                    text = formatedDate,
                    style = baseline.bodyLarge.copy(
                        fontFamily = bodyFontFamily
                    )
                )
            }
        }
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.padding_medium)))
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