package br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository

import br.ufu.gustavodejesus.buscacarrofipe.home.data.Resource
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.VehicleFipe

interface IVehicleRepository {

    suspend fun getVehiclesFipe(
        vehicleType: String,
        brandId: Int,
        modelId: Int
    ): Resource<List<VehicleFipe>>
}