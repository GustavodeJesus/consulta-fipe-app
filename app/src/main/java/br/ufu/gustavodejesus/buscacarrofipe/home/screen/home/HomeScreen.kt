package br.ufu.gustavodejesus.buscacarrofipe.home.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.ufu.gustavodejesus.buscacarrofipe.R
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.Brand
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.VehicleFipe
import br.ufu.gustavodejesus.buscacarrofipe.home.domain.model.VehicleModel
import br.ufu.gustavodejesus.buscacarrofipe.ui.theme.BuscaFipeTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateViewed: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showVehiclesBottomSheet by remember { mutableStateOf(false) }
    var showBrandsBottomSheet by remember { mutableStateOf(false) }
    var showModelsBottomSheet by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF4F5D75),
                titleContentColor = Color(0xFFFFFFFF),
            ),
            title = {
                Text("Busca Fipe App")
            },
            actions = {
                IconButton(onClick = onNavigateViewed) {
                    Icon(
                        imageVector = Icons.Filled.List,
                        contentDescription = "Visualizados",
                        tint = Color(0xFFFFFFFF)
                    )
                }
            },
        )
    }) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
                .padding(top = padding.calculateTopPadding())
                .padding(horizontal = 16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OptionChip(
                    viewModel.vehicleType.replaceFirstChar { it.uppercase() },
                    isEnabled = true
                ) {
                    showVehiclesBottomSheet = true
                }
                OptionChip(viewModel.brandSelected.name, isEnabled = viewModel.enabledBrandChip) {
                    showBrandsBottomSheet = true
                }
                OptionChip(
                    viewModel.vehicleModelSelected.name,
                    isEnabled = viewModel.enabledModelChip
                ) {
                    showModelsBottomSheet = true
                }
            }

            if (showVehiclesBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showVehiclesBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    val optionOnClick: (String) -> Unit = {
                        viewModel.enableBrand(it)
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showVehiclesBottomSheet = false
                            }
                        }
                    }

                    VehicleTypeOptions(optionOnClick = optionOnClick)
                }
            }

            if (showBrandsBottomSheet) {
                LaunchedEffect(Unit) {
                    viewModel.fetchBrands()
                }
                ModalBottomSheet(
                    onDismissRequest = {
                        showBrandsBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    val optionOnClick: (Brand) -> Unit = { brand ->
                        viewModel.enableModel(brand)
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBrandsBottomSheet = false
                            }
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        if (viewModel.brandState.isLoading) {
                            Loading()
                        } else if (!viewModel.brandState.error.isNullOrEmpty()) {
                            Text(text = viewModel.brandState.error.orEmpty())
                        } else {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp, horizontal = 16.dp),
                                text = "Escolha uma marca",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                            LazyColumn {
                                items(viewModel.brandState.brands.orEmpty()) { brand ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { optionOnClick(brand) },
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = brand.name, modifier = Modifier.padding(16.dp))
                                    }

                                    Divider(
                                        color = Color.LightGray,
                                        thickness = 0.5.dp,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            if (showModelsBottomSheet) {
                LaunchedEffect(Unit) {
                    viewModel.fetchModels()
                }
                ModalBottomSheet(
                    onDismissRequest = {
                        showModelsBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    val optionOnClick: (VehicleModel) -> Unit = { model ->
                        viewModel.setModelSelected(model)
                        viewModel.fetchVehicles()
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showModelsBottomSheet = false
                            }
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        if (viewModel.modelState.isLoading) {
                            Loading()
                        } else if (!viewModel.modelState.error.isNullOrEmpty()) {
                            Text(text = viewModel.modelState.error.orEmpty())
                        } else {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp, horizontal = 16.dp),
                                text = "Escolha um modelo",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                            LazyColumn {
                                items(viewModel.modelState.vehicleModels.orEmpty()) { model ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { optionOnClick(model) },
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = model.name, modifier = Modifier.padding(16.dp))
                                    }

                                    Divider(
                                        color = Color.LightGray,
                                        thickness = 0.5.dp,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            if (viewModel.vehicleState.isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(3f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Loading()
                }
            }

            if (!viewModel.vehicleState.vehicles.isNullOrEmpty()) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    content = {
                        items(viewModel.vehicleState.vehicles.orEmpty()) { vehicle ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                colors = CardDefaults.cardColors(),
                                elevation = CardDefaults.cardElevation(),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.White)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp)
                                    ) {
                                        Image(
                                            painter = vehicle.vehicleType.toImage(),
                                            contentDescription = null
                                        )
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            text = vehicle.brand + " - " + vehicle.model,
                                            style = TextStyle(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = vehicle.modelYear.toString(),
                                            style = TextStyle(fontSize = 12.sp)
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.End,
                                            text = vehicle.value,
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Blue
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }

            if (!viewModel.vehicleState.error.isNullOrEmpty()) {
                Text(text = viewModel.vehicleState.error.toString())
            }

            if (viewModel.vehicleState.vehicles.isNullOrEmpty() &&
                !viewModel.vehicleState.isLoading
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(3f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Selecione o Tipo de Veículo, Marca e Modelo.E veja os valores da tabela FIPE disponíveis.",
                        modifier = Modifier.padding(vertical = 32.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun String.toImage() =
    when (this) {
        "MOTORCYCLE" -> painterResource(id = R.drawable.moto)
        "CAR" -> painterResource(id = R.drawable.carro)
        else -> painterResource(id = R.drawable.caminhao)
    }


@Composable
private fun Loading() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        CircularProgressIndicator(
            modifier = Modifier.width(32.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            trackColor = MaterialTheme.colorScheme.secondary,
        )
        Text(text = "Carregando....")
        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Composable
fun VehicleTypeOptions(optionOnClick: (String) -> Unit) {
    Text(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        text = "Escolha uma opção de veículo",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { optionOnClick("carros") },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Carro", modifier = Modifier.padding(16.dp))
    }

    Divider(
        color = Color.LightGray,
        thickness = 0.5.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { optionOnClick("caminhoes") },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "caminhão", modifier = Modifier.padding(16.dp))
    }
    Divider(
        color = Color.LightGray,
        thickness = 0.5.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { optionOnClick("motos") },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Moto", modifier = Modifier.padding(16.dp))
    }

    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun OptionChip(
    text: String,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    AssistChip(
        onClick = onClick,
        enabled = isEnabled,
        label = { Text(text) },
        leadingIcon = {
            Icon(
                Icons.Filled.Settings,
                contentDescription = "Localized description",
                Modifier.size(AssistChipDefaults.IconSize)
            )
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun ListChipsBottomSheetPreview() {
    BuscaFipeTheme {
        HomeScreen(onNavigateViewed = {})
    }
}