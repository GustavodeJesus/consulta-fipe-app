package br.ufu.gustavodejesus.buscacarrofipe.home.data

import br.ufu.gustavodejesus.buscacarrofipe.home.data.remote.FipeApi
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.VehicleFipe
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository.IBrandRepository
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository.IModelRepository
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository.IVehicleRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleRepository @Inject constructor(
    private val api: FipeApi
) : IVehicleRepository {

    override suspend fun getVehiclesFipe(
        vehicleType: String,
        brandId: Int,
        modelId: Int
    ): Resource<List<VehicleFipe>> = coroutineScope {
        try {
            val years = api.getYears(
                vehicleType = vehicleType,
                brandId = brandId,
                modelId = modelId
            )
            val vehicles = years.map { year ->
                async {
                    api.getVehicles(
                        vehicleType = vehicleType,
                        brandId = brandId,
                        modelId = modelId,
                        modelYear = year.code
                    )
                }
            }

            Resource.Success(
                data = vehicles.awaitAll()
            )

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}
