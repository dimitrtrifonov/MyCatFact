package clarity.software.dimtrif.mycatfact

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatBreedsScreen()
        }
    }
}


@Composable
fun CatBreedsScreen(catViewModel: CatBreedsViewModel = viewModel()) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Cat Breeds", style = MaterialTheme.typography.labelMedium)

        when {
            catViewModel.isLoading.collectAsState().value -> CircularProgressIndicator(
                modifier = Modifier.padding(
                    16.dp
                )
            )

            catViewModel.errorMessage.collectAsState().value != null -> Text(
                text = catViewModel.errorMessage.collectAsState().value!!,
                color = MaterialTheme.colorScheme.error
            )

            else -> {
                LazyColumn {
                    items(catViewModel.catBreeds.value) { breed ->
                        CatBreedItem(breed)
                    }
                }
            }
        }
    }
}

@Composable
fun CatBreedItem(catBreed: CatBreed) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = catBreed.breed, style = MaterialTheme.typography.labelMedium)
            Text(text = "Origin: ${catBreed.origin}")
            Text(text = "Country: ${catBreed.country}")
            Text(text = "Coat: ${catBreed.coat}")
            Text(text = "Pattern: ${catBreed.pattern}")
        }
    }
}
