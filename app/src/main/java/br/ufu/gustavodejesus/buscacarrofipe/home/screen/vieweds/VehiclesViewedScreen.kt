package br.ufu.gustavodejesus.buscacarrofipe.home.screen.vieweds

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import br.ufu.gustavodejesus.buscacarrofipe.ui.theme.BuscaFipeTheme

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalFoundationApi
@Composable
fun VehiclesViewedScreen(
    modifier: Modifier = Modifier,
    viewModel: VehiclesViewedViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {

    Scaffold(modifier = modifier.padding(0.dp), topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF4F5D75),
                titleContentColor = Color(0xFFFFFFFF),
            ),
            title = {
                Text("Veículos visualizados")
            },
            navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Visualizados",
                        tint = Color(0xFFFFFFFF)
                    )
                }
            },
        )
    }) { padding ->

        LaunchedEffect(key1 = Unit) {
            viewModel.fetchVehiclesViewed()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
                .padding(top = padding.calculateTopPadding())
                .padding(horizontal = 16.dp)
        ) {

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
                        text = "Poxa, voce nao visualizou nenhum veículo",
                        modifier = Modifier.padding(vertical = 32.dp)
                    )
                }
            }

        }
    }
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

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun VehiclesViewedScreenPreview() {
    BuscaFipeTheme {
        VehiclesViewedScreen(onBackPressed = {})
    }
}

@Composable
private fun String.toImage() =
    when (this) {
        "MOTORCYCLE" -> painterResource(id = R.drawable.moto)
        "CAR" -> painterResource(id = R.drawable.carro)
        else -> painterResource(id = R.drawable.caminhao)
    }