package com.bitta.app.ui.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val PRODUCTS = "products"
private const val PRODUCTS_DISPENSER_ID_ARG = "dispenserId"
private const val DISPENSERS = "dispensers"

fun NavHostController.toProducts(dispenserId: Int) = navigate("$PRODUCTS/$dispenserId")

@Composable
fun AppNavigator(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = DISPENSERS,
) {
    NavHost(navController, startDestination, modifier = modifier) {
        composable(DISPENSERS) { Home(onDispenserSelected = navController::toProducts) }
        composable(
            "$PRODUCTS/{$PRODUCTS_DISPENSER_ID_ARG}",
            arguments = listOf(navArgument(PRODUCTS_DISPENSER_ID_ARG) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            ProductsSearch(
                backStackEntry.arguments?.getInt(PRODUCTS_DISPENSER_ID_ARG)!!,
                onBack = navController::popBackStack,
            )
        }
    }
}