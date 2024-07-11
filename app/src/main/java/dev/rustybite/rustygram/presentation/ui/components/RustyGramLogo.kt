package dev.rustybite.rustygram.presentation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    val painter = if (isSystemInDarkTheme()) R.drawable.rusty_gram_logo_dark else R.drawable.rusty_gram_logo_light
    Image(
        painter = painterResource(id = painter),
        contentDescription = stringResource(id = R.string.rusty_logo_description),
        modifier = modifier
            .size(dimensionResource(id = R.dimen.logo_size_medium)),

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