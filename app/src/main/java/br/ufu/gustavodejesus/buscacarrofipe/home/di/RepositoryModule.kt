package br.ufu.gustavodejesus.buscacarrofipe.home.di

import br.ufu.gustavodejesus.buscacarrofipe.home.data.BrandRepository
import br.ufu.gustavodejesus.buscacarrofipe.home.data.ModelRepository
import br.ufu.gustavodejesus.buscacarrofipe.home.data.VehicleRepository
import br.ufu.gustavodejesus.buscacarrofipe.home.data.VehiclesViewedRepository
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository.IBrandRepository
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository.IModelRepository
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository.IVehicleRepository
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository.IVehiclesViewedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBrandRepository(
        brandRepository: BrandRepository
    ): IBrandRepository

    @Binds
    @Singleton
    abstract fun bindModelRepository(
        modelRepository: ModelRepository
    ): IModelRepository

    @Binds
    @Singleton
    abstract fun bindVehicleRepository(
        vehicleRepository: VehicleRepository
    ): IVehicleRepository

    @Binds
    @Singleton
    abstract fun bindVehiclesViewedRepository(
        vehiclesViewedRepository: VehiclesViewedRepository
    ): IVehiclesViewedRepository
}
