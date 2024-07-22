package dev.rustybite.rustygram.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.semantics.Role
import dev.rustybite.rustygram.R

@Composable
fun LabguageOptions(
    languageOptions: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .selectableGroup()
    ) {
        languageOptions.forEach { language ->
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.radio_button_height))
                    .selectable(
                        selected = (language == selectedOption),
                        onClick = { onOptionSelected(language) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = language)
                RadioButton(
                    selected = language == selectedOption,
                    onClick = null
                )
            }
        }
    }
}