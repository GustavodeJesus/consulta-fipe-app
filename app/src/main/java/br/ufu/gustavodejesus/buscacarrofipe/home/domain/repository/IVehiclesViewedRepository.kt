package br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository

import br.ufu.gustavodejesus.buscacarrofipe.home.data.Resource
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.VehicleFipe

interface IVehiclesViewedRepository {
    suspend fun getVehiclesViewed(): Resource<List<VehicleFipe>>
}