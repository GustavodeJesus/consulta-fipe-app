package br.ufu.gustavodejesus.buscacarrofipe

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.ufu.gustavodejesus.buscacarrofipe.home.screen.home.HomeScreen
import br.ufu.gustavodejesus.buscacarrofipe.home.screen.vieweds.VehiclesViewedScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SetupNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(route = "home") {
            HomeScreen(
                modifier = modifier,
                onNavigateViewed = {
                    navController.navigate("viewed")
                }
            )
        }

        composable(route = "viewed") {
            VehiclesViewedScreen(
                modifier = modifier, onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}