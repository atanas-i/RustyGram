package dev.rustybite.rustygram.presentation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.ui.theme.RustyGramTheme
import dev.rustybite.rustygram.presentation.ui.theme.baseline
import dev.rustybite.rustygram.presentation.ui.theme.displayFontFamily

@Composable
fun RustyPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = Color.White
) {
    val colors = ButtonDefaults.buttonColors(
        containerColor = backgroundColor,
        contentColor = contentColor,
        disabledContainerColor = backgroundColor
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = dimensionResource(id = R.dimen.button_height)),
        colors = colors,
        enabled = enabled
    ) {
        Text(
            text = if (enabled) text else "",
            style = baseline.labelLarge.copy(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.W600,
                fontSize = TextUnit(dimensionResource(id = R.dimen.button_text_size).value, TextUnitType.Sp)
            )
        )
    }
}


@Composable
fun RustySecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = MaterialTheme.colorScheme.onBackground
) {
    val colors = ButtonDefaults.buttonColors(
        containerColor = backgroundColor,
        contentColor = contentColor,
    )

    TextButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = dimensionResource(id = R.dimen.sec_button_height))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_radius_round))),
        border = BorderStroke(
            width = dimensionResource(id = R.dimen.tf_border_width_small),
            color = MaterialTheme.colorScheme.onBackground.copy(.6f),
        ),
        colors = colors,
        enabled = enabled,
    ) {
        Text(
            text = if (enabled) text else "",
            style = baseline.labelLarge.copy(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.W600,
                fontSize = TextUnit(dimensionResource(id = R.dimen.button_text_size).value, TextUnitType.Sp)
            )
        )
    }
}

@Composable
fun RustySemiPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = MaterialTheme.colorScheme.primary
) {
    val colors = ButtonDefaults.buttonColors(
        containerColor = backgroundColor,
        contentColor = contentColor,
    )

    TextButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = dimensionResource(id = R.dimen.sec_button_height))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_radius_round))),
        border = BorderStroke(
            width = dimensionResource(id = R.dimen.tf_border_width_small),
            color = MaterialTheme.colorScheme.primary,
        ),
        colors = colors,
        enabled = enabled,
    ) {
        Text(
            text = if (enabled) text else "",
            style = baseline.labelLarge.copy(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.W600,
                fontSize = TextUnit(dimensionResource(id = R.dimen.button_text_size).value, TextUnitType.Sp)
            )
        )
    }
}


@Preview(showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun ButtonsPreview() {
    RustyGramTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                RustyPrimaryButton(
                    text = "Primary Button",
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                )
                RustySecondaryButton(
                    text = "Secondary Button",
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                )
                RustySemiPrimaryButton(
                    text = "Semi Primary Button",
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                )
            }
        }
    }
}