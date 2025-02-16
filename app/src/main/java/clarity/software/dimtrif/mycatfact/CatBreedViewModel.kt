package clarity.software.dimtrif.mycatfact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatBreedsViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO // Default to IO
) : ViewModel() {
    private val _catBreeds = MutableStateFlow<List<CatBreed>>(emptyList())
    val catBreeds: StateFlow<List<CatBreed>> = _catBreeds.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _cachedListShown = MutableStateFlow(false)
    val cachedListShown: StateFlow<Boolean> = _cachedListShown.asStateFlow()

    init {
        fetchCatBreeds()
    }

    private fun fetchCatBreeds() {
        viewModelScope.launch {
            if (CatFactApp.networkObserver.isConnected.value) {
                try {
                    _isLoading.value = true
                    _catBreeds.value = RetrofitInstance.api.getCatBreeds().data
                    saveCatBreedListToDatabase()
                    _errorMessage.value = null
                } catch (e: Exception) {
                    _errorMessage.value = "Failed to load cat breeds: ${e.message}"
                } finally {
                    _isLoading.value = false
                }
            } else {
                _catBreeds.value = getCatBreedList()
                _isLoading.value = false
                _cachedListShown.value = true
            }
        }
    }

    private suspend fun saveCatBreedListToDatabase() {
        withContext(dispatcher) {
            CatFactApp.database.catBreedDao().insertAll(_catBreeds.value)
        }
    }

    private suspend fun getCatBreedList(): List<CatBreed> {
        return withContext(dispatcher) {
            CatFactApp.database.catBreedDao().getAllBreeds()
        }
    }

}


