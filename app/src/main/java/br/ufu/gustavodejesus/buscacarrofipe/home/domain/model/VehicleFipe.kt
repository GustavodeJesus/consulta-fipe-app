package br.ufu.gustavodejesus.buscacarrofipe.home.domain.model

data class VehicleFipe(
    val value: String,
    val brand: String,
    val model: String,
    val modelYear: Int,
    val fuelType: String,
    val fipeCode: String,
    val referenceMonth: String,
    val fuelTypeAbbreviation: String,
    val vehicleType: String,
)