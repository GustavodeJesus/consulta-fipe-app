package br.ufu.gustavodejesus.buscacarrofipe.home.data

import br.ufu.gustavodejesus.buscacarrofipe.home.data.remote.FipeApi
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.VehicleFipe
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository.IVehiclesViewedRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehiclesViewedRepository @Inject constructor(
    private val api: FipeApi
) : IVehiclesViewedRepository {

    override suspend fun getVehiclesViewed(): Resource<List<VehicleFipe>> =
        try {
            Resource.Success(
                data = api.getVehiclesViewed()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }


}
