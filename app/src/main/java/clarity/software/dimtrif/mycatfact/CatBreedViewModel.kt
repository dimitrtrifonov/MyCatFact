package clarity.software.dimtrif.mycatfact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatBreedsViewModel : ViewModel() {
    private val _catBreeds = MutableStateFlow<List<CatBreed>>(emptyList())
    val catBreeds: StateFlow<List<CatBreed>> = _catBreeds.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchCatBreeds()
    }

    fun fetchCatBreeds() {
        viewModelScope.launch {
                try {
                    _isLoading.value = true
                    _catBreeds.value = RetrofitInstance.api.getCatBreeds().data // Set retrieved list
                    _errorMessage.value = null
                } catch (e: Exception) {
                    _errorMessage.value = "Failed to load cat breeds: ${e.message}"
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }


