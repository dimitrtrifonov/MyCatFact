package clarity.software.dimtrif.mycatfact

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatBreedsScreen()
        }
    }
}


@Composable
fun CatBreedsScreen(catViewModel: CatBreedsViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Cat Breeds", style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
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
    if (catViewModel.cachedListShown.collectAsState().value) {
        Toast.makeText(LocalContext.current, "Cached list shown", Toast.LENGTH_SHORT).show()
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
