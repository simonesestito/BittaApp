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
private const val PRODUCT_INFO = "productInfo"
private const val PRODUCT_INFO_ID_ARG = "productId"
private const val REPORTS = "reports"
private const val REPORTS_DISPENSER_ID_ARG = "dispenserId"
private const val NEW_REPORT = "newReport"

fun NavHostController.toProducts(dispenserId: Int) = navigate("$PRODUCTS/$dispenserId")
fun NavHostController.toProductInfo(productId: Int) = navigate("$PRODUCT_INFO/$productId")
fun NavHostController.toReports(dispenserId: Int) = navigate("$REPORTS/$dispenserId")
fun NavHostController.toNewReport(dispenserId: Int) = navigate("$NEW_REPORT/$dispenserId")

@Composable
fun AppNavigator(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = DISPENSERS,
) {
    NavHost(navController, startDestination, modifier = modifier) {
        composable(DISPENSERS) {
            Home(
                onDispenserSelected = navController::toProducts,
                onNewReport = navController::toNewReport,
                onShowReports = navController::toReports,
            )
        }

        composable(
            "$PRODUCTS/{$PRODUCTS_DISPENSER_ID_ARG}",
            arguments = listOf(navArgument(PRODUCTS_DISPENSER_ID_ARG) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            ProductsSearch(
                backStackEntry.arguments?.getInt(PRODUCTS_DISPENSER_ID_ARG)!!,
                onBack = navController::popBackStack,
                onProductInfo = { navController.toProductInfo(it.id) },
                onProductPurchase = { /* TODO*/ },
            )
        }

        composable(
            "$PRODUCT_INFO/{$PRODUCT_INFO_ID_ARG}",
            arguments = listOf(navArgument(PRODUCT_INFO_ID_ARG) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            ProductInfo(
                backStackEntry.arguments?.getInt(PRODUCT_INFO_ID_ARG)!!,
                onBack = navController::popBackStack,
            )
        }

        composable(
            "$REPORTS/{$REPORTS_DISPENSER_ID_ARG}",
            arguments = listOf(navArgument(REPORTS_DISPENSER_ID_ARG) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val dispenserId = backStackEntry.arguments?.getInt(REPORTS_DISPENSER_ID_ARG)!!
            ReportsList(
                dispenserId,
                onBack = navController::popBackStack,
                onNewReport = { navController.toNewReport(dispenserId) },
            )
        }

        composable(
            "$NEW_REPORT/{$REPORTS_DISPENSER_ID_ARG}",
            arguments = listOf(navArgument(REPORTS_DISPENSER_ID_ARG) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            NewReport(
                backStackEntry.arguments?.getInt(REPORTS_DISPENSER_ID_ARG)!!,
                onBack = navController::popBackStack,
                onTypeSelected = { /* TODO */ },
            )
        }
    }
}