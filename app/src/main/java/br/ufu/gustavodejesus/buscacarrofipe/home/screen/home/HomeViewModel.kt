package br.ufu.gustavodejesus.buscacarrofipe.home.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.ufu.gustavodejesus.buscacarrofipe.home.data.Resource
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.Brand
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.VehicleModel
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository.IBrandRepository
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository.IModelRepository
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository.IVehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val brandRepository: IBrandRepository,
    private val modelRepository: IModelRepository,
    private val vehicleRepository: IVehicleRepository
) : ViewModel() {

    var vehicleType by mutableStateOf("Tipo veículo")
        private set

    var brandSelected by mutableStateOf(Brand("Marca", -1))
        private set

    var vehicleModelSelected by mutableStateOf(VehicleModel("Modelo", -1))
        private set

    fun enableBrand(vehicleType: String) {
        enabledBrandChip = true
        this.vehicleType = vehicleType
    }

    var enabledBrandChip by mutableStateOf(false)
        private set

    var enabledModelChip by mutableStateOf(false)
        private set

    var brandState by mutableStateOf(BrandState())
        private set

    var modelState by mutableStateOf(ModelState())
        private set

    var vehicleState by mutableStateOf(VehicleState())
        private set

    fun fetchBrands() {
        viewModelScope.launch {
            brandState = brandState.copy(
                isLoading = true,
                error = null,
                brands = null
            )
            delay(3000L)
            when (val result = brandRepository.getBrands(vehicleType = vehicleType)) {
                is Resource.Success -> {
                    brandState = brandState.copy(
                        brands = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    brandState = brandState.copy(
                        brands = null,
                        isLoading = false,
                        error = result.message,
                    )
                }
            }
        }
    }

    fun enableModel(brand: Brand) {
        enabledModelChip = true
        this.brandSelected = brand
    }

    fun setModelSelected(vehicleModel: VehicleModel) {
        this.vehicleModelSelected = vehicleModel
    }

    fun fetchVehicles() {
        viewModelScope.launch {
            vehicleState = vehicleState.copy(
                isLoading = true,
                error = null,
                vehicles = null
            )
            delay(3000L)
            when (val result =
                vehicleRepository.getVehiclesFipe(
                    vehicleType,
                    brandSelected.code,
                    vehicleModelSelected.code
                )
            ) {
                is Resource.Success -> {
                    vehicleState = vehicleState.copy(
                        vehicles = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    vehicleState = vehicleState.copy(
                        vehicles = null,
                        isLoading = false,
                        error = result.message,
                    )
                }
            }
        }
    }

    fun fetchModels() {
        viewModelScope.launch {
            modelState = modelState.copy(
                isLoading = true,
                error = null,
                vehicleModels = null
            )
            delay(3000L)
            when (val result = modelRepository.getModels(
                vehicleType = vehicleType,
                brandId = brandSelected.code
            )) {
                is Resource.Success -> {
                    modelState = modelState.copy(
                        vehicleModels = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    modelState = modelState.copy(
                        vehicleModels = null,
                        isLoading = false,
                        error = result.message,
                    )
                }
            }
        }
    }

}