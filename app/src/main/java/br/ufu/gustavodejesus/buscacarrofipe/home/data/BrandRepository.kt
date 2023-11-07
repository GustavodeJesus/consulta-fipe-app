package br.ufu.gustavodejesus.buscacarrofipe.home.data

import br.ufu.gustavodejesus.buscacarrofipe.home.data.remote.FipeApi
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.Brand
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository.IBrandRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BrandRepository @Inject constructor(
    private val api: FipeApi
) : IBrandRepository {

    override suspend fun getBrands(vehicleType: String): Resource<List<Brand>> =
        try {
            Resource.Success(
                data = api.getBrands(vehicleType = vehicleType)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
}