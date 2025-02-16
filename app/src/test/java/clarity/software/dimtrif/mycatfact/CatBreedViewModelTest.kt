package clarity.software.dimtrif.mycatfact

import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CatViewModelTest {

    private val mockCatBreeds = listOf(
        CatBreed(1, "Siamese", "Siamese", "Siamese", "Siamese", "Siamese"),
        CatBreed(2, "Persian", "Persian", "Persian", "Persian", "Persian")
    )
    private lateinit var viewModel: CatBreedsViewModel

    @Before
    fun setUp() {
        mockkObject(CatFactApp)
        mockkObject(RetrofitInstance)
        every { CatFactApp.networkObserver.isConnected } returns MutableStateFlow(true)
        coEvery { RetrofitInstance.api.getCatBreeds() } returns CatBreedsResponse(mockCatBreeds)
        coEvery { CatFactApp.database.catBreedDao().insertAll(any()) } just Runs // Stubs insertAll() to do nothing
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun catBreedsViewModelTest() = runTest {
        viewModel = CatBreedsViewModel(StandardTestDispatcher())
        advanceUntilIdle()
        assertEquals(false, viewModel.isLoading.value)
        assertEquals(null, viewModel.errorMessage.value)
        assertEquals(mockCatBreeds, viewModel.catBreeds.value)
    }
}