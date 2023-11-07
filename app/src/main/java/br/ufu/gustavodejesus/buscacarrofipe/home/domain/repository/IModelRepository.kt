package br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository

import br.ufu.gustavodejesus.buscacarrofipe.home.data.Resource
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.VehicleModel

interface IModelRepository {
    suspend fun getModels(
        vehicleType: String,
        brandId: Int
    ): Resource<List<VehicleModel>>
}