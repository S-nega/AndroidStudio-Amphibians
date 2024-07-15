package kz.android.amphibians.data

import kz.android.amphibians.model.Amphibians
import kz.android.amphibians.network.AmphibianApiService

interface AmphibiansRepository {
    suspend fun getAmphibians(): List<Amphibians>
}

class NetworkAmphibiansRepository(
    private val amphibianApiService: AmphibianApiService
) : AmphibiansRepository{
    override suspend fun getAmphibians(): List<Amphibians> = amphibianApiService.getAmphibians()
}