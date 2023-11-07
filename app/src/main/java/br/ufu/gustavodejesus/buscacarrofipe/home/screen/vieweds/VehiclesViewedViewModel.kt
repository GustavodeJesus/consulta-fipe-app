package br.ufu.gustavodejesus.buscacarrofipe.home.screen.vieweds

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.ufu.gustavodejesus.buscacarrofipe.home.data.Resource
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository.IVehiclesViewedRepository
import br.ufu.gustavodejesus.buscacarrofipe.home.screen.home.VehicleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VehiclesViewedViewModel @Inject constructor(
    private val vehiclesViewedRepository: IVehiclesViewedRepository
) : ViewModel() {

    var vehicleState by mutableStateOf(VehicleState())
        private set

    fun fetchVehiclesViewed() {
        viewModelScope.launch {
            vehicleState = vehicleState.copy(
                isLoading = true,
                error = null,
                vehicles = null
            )
            delay(3000L)
            when (val result =
                vehiclesViewedRepository.getVehiclesViewed()
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
}
