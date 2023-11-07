package br.ufu.gustavodejesus.buscacarrofipe.home.data.remote

import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.Brand
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.VehicleModel
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.VehicleFipe
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.Year
import retrofit2.http.GET
import retrofit2.http.Path

interface FipeApi {

    @GET("{vehicleType}/brands")
    suspend fun getBrands(@Path("vehicleType") vehicleType: String): List<Brand>

    @GET("{vehicleType}/brands/{brandId}/models")
    suspend fun getModels(
        @Path("vehicleType") vehicleType: String,
        @Path("brandId") brandId: Int,
    ): List<VehicleModel>

    @GET("{vehicleType}/brands/{brandId}/models/{modelId}/years")
    suspend fun getYears(
        @Path("vehicleType") vehicleType: String,
        @Path("brandId") brandId: Int,
        @Path("modelId") modelId: Int
    ): List<Year>

    @GET("{vehicleType}/brands/{brandId}/models/{modelId}/years/{modelYear}")
    suspend fun getVehicles(
        @Path("vehicleType") vehicleType: String,
        @Path("brandId") brandId: Int,
        @Path("modelId") modelId: Int,
        @Path("modelYear") modelYear: String
    ): VehicleFipe

    @GET("vehicles-viewed")
    suspend fun getVehiclesViewed(): List<VehicleFipe>
}
