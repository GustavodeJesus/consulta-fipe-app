package br.ufu.gustavodejesus.buscacarrofipe.home.data

import br.ufu.gustavodejesus.buscacarrofipe.home.data.remote.FipeApi
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.Brand
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.VehicleModel
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository.IBrandRepository
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository.IModelRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelRepository @Inject constructor(
    private val api: FipeApi
) : IModelRepository {

    override suspend fun getModels(vehicleType: String, brandId: Int): Resource<List<VehicleModel>> =
        try {
            Resource.Success(
                data = api.getModels(
                    vehicleType = vehicleType,
                    brandId = brandId
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }

}
