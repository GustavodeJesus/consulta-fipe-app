package br.ufu.gustavodejesus.buscacarrofipe.home.screen.home

import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.VehicleModel

data class ModelState(
    val vehicleModels: List<VehicleModel>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)