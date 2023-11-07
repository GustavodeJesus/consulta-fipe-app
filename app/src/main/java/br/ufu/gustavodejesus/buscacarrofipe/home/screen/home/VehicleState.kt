package br.ufu.gustavodejesus.buscacarrofipe.home.screen.home

import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.VehicleFipe

data class VehicleState(
    val vehicles: List<VehicleFipe>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)