package dev.rustybite.rustygram.presentation.posts.create_post.edit_photo

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.rustybite.rustygram.R

@Composable
fun FilterEffects(
    uri: Uri,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(text = "Normal")
                Card(
                    modifier = modifier
                        .height(100.dp)
                        .width(100.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(uri)
                            .build(),
                        contentDescription = stringResource(R.string.selected_image),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(text = "Paris")
                Card(
                    modifier = modifier
                        .height(100.dp)
                        .width(100.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(uri)
                            .build(),
                        contentDescription = stringResource(R.string.selected_image),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(text = "Los Angeles")
                Card(
                    modifier = modifier
                        .height(100.dp)
                        .width(100.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(uri)
                            .build(),
                        contentDescription = stringResource(R.string.selected_image),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(text = "Bahamas")
                Card(
                    modifier = modifier
                        .height(100.dp)
                        .width(100.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(uri)
                            .build(),
                        contentDescription = stringResource(R.string.selected_image),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(text = "Hawaii")
                Card(
                    modifier = modifier
                        .height(100.dp)
                        .width(100.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(uri)
                            .build(),
                        contentDescription = stringResource(R.string.selected_image),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}