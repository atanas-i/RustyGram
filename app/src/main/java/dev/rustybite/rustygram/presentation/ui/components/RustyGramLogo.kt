package dev.rustybite.rustygram.presentation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.presentation.ui.theme.RustyGramTheme
import dev.rustybite.rustygram.presentation.ui.theme.baseline

@Composable
fun RustyGramLogo(
    modifier: Modifier = Modifier
) {
    Text(
        text = "RG",
        modifier = modifier
            .width(dimensionResource(id = R.dimen.logo_width))
            .height(dimensionResource(id = R.dimen.logo_height))
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                ),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_radius_medium)),
            ),
        textAlign = TextAlign.Center,
        color = Color.White,
        style = baseline.headlineLarge.copy(
            fontFamily = FontFamily(Font(R.font.bodoni_moda_sc)),
            fontWeight = FontWeight.W600,
        ),
        textDecoration = TextDecoration.LineThrough
    )
}


@Composable
fun RustyBiteLogo(
    modifier: Modifier = Modifier
) {
    Text(
        text = "RustyBite",
        modifier = modifier,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        style = baseline.headlineLarge.copy(
            fontFamily = FontFamily(Font(R.font.playwrite_us_trad)),
            fontWeight = FontWeight.W600,
        ),
        textDecoration = TextDecoration.Underline
    )
}


@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun RustyGramLogoPreview() {
    RustyGramTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                RustyGramLogo()
                RustyBiteLogo()
            }
        }
    }
}