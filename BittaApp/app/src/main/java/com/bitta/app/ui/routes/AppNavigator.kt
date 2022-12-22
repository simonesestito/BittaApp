package com.bitta.app.ui.routes

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bitta.app.R
import com.bitta.app.SnackbarInfo
import com.bitta.app.model.UserReportKind
import com.bitta.app.ui.routes.reports.UserProductReport
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

private const val PRODUCTS = "products"
private const val PRODUCTS_DISPENSER_ID_ARG = "dispenserId"
private const val DISPENSERS = "dispensers"
private const val PRODUCT_INFO = "productInfo"
private const val PRODUCT_INFO_ID_ARG = "productId"
private const val REPORTS = "reports"
private const val REPORTS_DISPENSER_ID_ARG = "dispenserId"
private const val NEW_REPORT = "newReport"
private const val NEW_REPORT_PRODUCT_DETAILS = "newReportDetails/product"
private const val NEW_REPORT_OTHER_DETAILS = "newReportDetails/other"
private const val NEW_REPORT_CHANGE_DETAILS = "newReportDetails/change"
private const val NEW_REPORT_DAMAGED_DETAILS = "newReportDetails/damaged"

fun NavHostController.toProducts(dispenserId: Int) = navigate("$PRODUCTS/$dispenserId")
fun NavHostController.toProductInfo(productId: Int) = navigate("$PRODUCT_INFO/$productId")
fun NavHostController.toReports(dispenserId: Int) = navigate("$REPORTS/$dispenserId")
fun NavHostController.toNewReport(dispenserId: Int) = navigate("$NEW_REPORT/$dispenserId")
fun NavHostController.toNewReportDetails(dispenserId: Int, kind: UserReportKind) {
    val route = when (kind) {
        UserReportKind.PRODUCT_DELIVERY -> NEW_REPORT_PRODUCT_DETAILS
        UserReportKind.MISSING_CHANGE -> NEW_REPORT_CHANGE_DETAILS
        UserReportKind.DAMAGED_DISPENSER -> NEW_REPORT_DAMAGED_DETAILS
        UserReportKind.OTHER -> NEW_REPORT_OTHER_DETAILS
    }
    navigate("$route/$dispenserId")
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun AppNavigator(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = DISPENSERS,
) {
    val snackbarHomeChannel = Channel<SnackbarInfo>(capacity = 2)

    NavHost(navController, startDestination, modifier = modifier) {
        composable(DISPENSERS) {
            Home(
                onDispenserSelected = navController::toProducts,
                onNewReport = navController::toNewReport,
                onShowReports = navController::toReports,
                snackbarChannel = snackbarHomeChannel,
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
            val dispenserId = backStackEntry.arguments?.getInt(REPORTS_DISPENSER_ID_ARG)!!
            NewReport(
                dispenserId,
                onBack = navController::popBackStack,
                onTypeSelected = { navController.toNewReportDetails(dispenserId, it) },
            )
        }

        val onReportCancelled: () -> Unit = {
            GlobalScope.launch {
                snackbarHomeChannel.send(
                    SnackbarInfo(R.string.report_cancelled_snackbar_title)
                )
            }
        }

        val onReportSent: (() -> Unit) -> Unit = { cancel ->
            Log.d("REPORT", "onReportSent")
            navController.popBackStack(
                DISPENSERS, inclusive = false
            )
            GlobalScope.launch {
                Log.d("REPORT", "sending to channel")
                snackbarHomeChannel.send(
                    SnackbarInfo(
                        message = R.string.report_sent_snackbar_title,
                        actionLabel = R.string.report_sent_snackbar_action_label,
                        onAction = {
                            cancel()
                            onReportCancelled()
                        },
                    )
                )
                Log.d("REPORT", "sent to channel")
            }
        }

        composable(
            "$NEW_REPORT_PRODUCT_DETAILS/{$REPORTS_DISPENSER_ID_ARG}",
            arguments = listOf(navArgument(REPORTS_DISPENSER_ID_ARG) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            UserProductReport(
                backStackEntry.arguments?.getInt(REPORTS_DISPENSER_ID_ARG)!!,
                onBack = navController::popBackStack,
                onReportSent = onReportSent,
            )
        }

        composable(
            "$NEW_REPORT_DAMAGED_DETAILS/{$REPORTS_DISPENSER_ID_ARG}",
            arguments = listOf(navArgument(REPORTS_DISPENSER_ID_ARG) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            // TODO
            UserProductReport(
                backStackEntry.arguments?.getInt(REPORTS_DISPENSER_ID_ARG)!!,
                onBack = navController::popBackStack,
                onReportSent = onReportSent,
            )
        }

        composable(
            "$NEW_REPORT_CHANGE_DETAILS/{$REPORTS_DISPENSER_ID_ARG}",
            arguments = listOf(navArgument(REPORTS_DISPENSER_ID_ARG) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            // TODO
            UserProductReport(
                backStackEntry.arguments?.getInt(REPORTS_DISPENSER_ID_ARG)!!,
                onBack = navController::popBackStack,
                onReportSent = onReportSent,
            )
        }

        composable(
            "$NEW_REPORT_OTHER_DETAILS/{$REPORTS_DISPENSER_ID_ARG}",
            arguments = listOf(navArgument(REPORTS_DISPENSER_ID_ARG) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            // TODO
            UserProductReport(
                backStackEntry.arguments?.getInt(REPORTS_DISPENSER_ID_ARG)!!,
                onBack = navController::popBackStack,
                onReportSent = onReportSent,
            )
        }
    }
}