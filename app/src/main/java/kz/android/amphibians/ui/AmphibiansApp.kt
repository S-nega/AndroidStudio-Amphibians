@file:OptIn(ExperimentalMaterial3Api::class)
package kz.android.amphibians.ui
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kz.android.amphibians.ui.screens.AmphibianViewModel
import kz.android.amphibians.ui.screens.HomeScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import kz.android.amphibians.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmphibiansApp(){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { AmphibiansTopAppBar(scrollBehavior = scrollBehavior)}
    ) {
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 65.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            val amphibianViewModel: AmphibianViewModel =
                viewModel(factory = AmphibianViewModel.Factory)
            HomeScreen(
                amphibiansUiState = amphibianViewModel.amphibiansUiState,
                retryAction = amphibianViewModel::getAmphibians,
                contentPadding = it
            )
        }
    }
}

@Composable
fun AmphibiansTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}
