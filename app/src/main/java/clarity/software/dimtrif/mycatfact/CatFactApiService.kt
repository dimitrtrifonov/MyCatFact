package clarity.software.dimtrif.mycatfact

import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface CatFactApiService {
    @GET("breeds")
    suspend fun getCatBreeds(): CatBreedsResponse
}

// Singleton object to create Retrofit instance
object RetrofitInstance {
    private const val BASE_URL = "https://catfact.ninja/"

    val api: CatFactApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatFactApiService::class.java)
    }
}
