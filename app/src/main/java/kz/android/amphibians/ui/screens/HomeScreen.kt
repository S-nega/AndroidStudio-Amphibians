package kz.android.amphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kz.android.amphibians.R
import kz.android.amphibians.model.Amphibians

@Composable
fun HomeScreen(
    amphibiansUiState: AmphibiansUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    when(amphibiansUiState){
        AmphibiansUiState.Loading -> LoadingScreen(
            modifier = modifier.fillMaxSize())
        is AmphibiansUiState.Success -> AmphibiansGridScreen(
            amphibians = amphibiansUiState.amphibians,
            modifier = Modifier.fillMaxSize())
        AmphibiansUiState.Error -> ErrorScreen(
            retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(R.drawable.ic_connection_error),
            contentDescription = stringResource(R.string.loading_failed)
        )
        Text(text = stringResource(R.string.loading_failed))
        Button(onClick = retryAction) {
            Text(text = stringResource(R.string.retry))
        }
    }
    

}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun AmphibiansGridScreen(
    amphibians: List<Amphibians>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    LazyColumn (
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding
    ){
        items(items = amphibians, key = {amphibian -> amphibian.name}){
            amphibian ->
            AmphibianCard(
                amphibian = amphibian,
                modifier = modifier
                    .padding(top = dimensionResource(R.dimen.padding_medium))
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun AmphibianCard(
    amphibian: Amphibians,
    modifier: Modifier = Modifier,
){
    Card (
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)

    ){
        Column(
            modifier = modifier
                .fillMaxWidth(),
        ) {
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small)),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                text = amphibian.name + " - " + amphibian.type
            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(amphibian.imgSrc)
                    .crossfade(true)
                    .build(),
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                contentDescription = amphibian.name,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small)),
                style = MaterialTheme.typography.bodyMedium,
                text = amphibian.description
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AmphibianCardPreview(){
    val mockData = List(10) { Amphibians("", "Lorem Ipsum - $it",
        "$it",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do" +
                " eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad" +
                " minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip" +
                " ex ea commodo consequat.",) }
    AmphibiansGridScreen(mockData)
}
