package br.ufu.gustavodejesus.buscacarrofipe.home.domain.repository

import br.ufu.gustavodejesus.buscacarrofipe.home.data.Resource
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.Brand

interface IBrandRepository {
    suspend fun getBrands(vehicleType: String): Resource<List<Brand>>
}