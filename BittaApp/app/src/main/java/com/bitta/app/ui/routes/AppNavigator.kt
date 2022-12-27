package com.bitta.app.ui.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bitta.app.R
import com.bitta.app.model.UserReportKind
import com.bitta.app.ui.routes.reports.UserChangeReport
import com.bitta.app.ui.routes.reports.UserDamageReport
import com.bitta.app.ui.routes.reports.UserOtherReport
import com.bitta.app.ui.routes.reports.UserProductReport
import com.bitta.app.utils.SnackbarInfo
import com.bitta.app.utils.getPreferences
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
private const val POST_PURCHASE_DELIVERY = "postPurchaseDelivery"

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

fun NavHostController.toPostPurchaseDelivery(dispenserId: Int) =
    navigate("$POST_PURCHASE_DELIVERY/$dispenserId")

@Composable
fun AppNavigator(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = DISPENSERS,
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHomeChannel = Channel<SnackbarInfo>(capacity = 1)
    val productsListSnackbarChannel = Channel<String>(capacity = 1)
    val preferences = LocalContext.current.getPreferences()

    // Reload last purchase, if not yet completed.
    LaunchedEffect(true) {
        val ongoingPurchaseDispenserId = preferences.ongoingPurchaseDispenserId
        if (ongoingPurchaseDispenserId != null) {
            // There is a purchase still not completed.
            navController.toPostPurchaseDelivery(ongoingPurchaseDispenserId)
        }
    }

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
            val dispenserId = backStackEntry.arguments?.getInt(PRODUCTS_DISPENSER_ID_ARG)!!
            ProductsSearch(
                dispenserId,
                snackbarChannel = productsListSnackbarChannel,
                onBack = navController::popBackStack,
                onProductInfo = { navController.toProductInfo(it.id) },
                onProductPurchased = { navController.toPostPurchaseDelivery(dispenserId) },
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
            coroutineScope.launch {
                snackbarHomeChannel.send(
                    SnackbarInfo(R.string.report_cancelled_snackbar_title)
                )
            }
        }

        val onReportSent: (() -> Unit) -> Unit = { cancel ->
            navController.popBackStack(
                DISPENSERS, inclusive = false
            )
            coroutineScope.launch {
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
            UserDamageReport(
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
            UserChangeReport(
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
            UserOtherReport(
                backStackEntry.arguments?.getInt(REPORTS_DISPENSER_ID_ARG)!!,
                onBack = navController::popBackStack,
                onReportSent = onReportSent,
            )
        }

        composable(
            "$POST_PURCHASE_DELIVERY/{$PRODUCTS_DISPENSER_ID_ARG}",
            arguments = listOf(navArgument(PRODUCTS_DISPENSER_ID_ARG) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val purchaseCancelledString = stringResource(R.string.purchase_cancelled_message)
            PostPurchaseDelivery(
                backStackEntry.arguments?.getInt(PRODUCTS_DISPENSER_ID_ARG)!!,
                onPurchaseCancelled = {
                    navController.popBackStack()
                    coroutineScope.launch { productsListSnackbarChannel.send(purchaseCancelledString) }
                },
                onProductDelivered = { /*TODO*/ },
            )
        }
    }
}