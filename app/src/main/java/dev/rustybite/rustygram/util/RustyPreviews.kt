package dev.rustybite.rustygram.util

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.rustybite.rustygram.presentation.ui.components.RustyTextField
import dev.rustybite.rustygram.presentation.ui.theme.RustyGramTheme

@Preview(showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun RustyTextFieldPreview() {
    RustyGramTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                RustyTextField(
                    value = "",
                    onValueChange = {},
                    label = "Something",
                    placeholder = "Someting"
                )
            }
        }
    }
}