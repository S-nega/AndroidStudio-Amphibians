package kz.android.amphibians.network

import kz.android.amphibians.model.Amphibians
import retrofit2.http.GET

interface AmphibianApiService {

    @GET("amphibians")
    suspend fun getAmphibians(): List<Amphibians>
}