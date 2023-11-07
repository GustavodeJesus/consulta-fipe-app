package br.ufu.gustavodejesus.buscacarrofipe.home.screen.home

import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.Brand

data class BrandState(
    val brands: List<Brand>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)