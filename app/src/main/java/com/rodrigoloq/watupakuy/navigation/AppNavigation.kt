package com.rodrigoloq.watupakuy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rodrigoloq.watupakuy.feature.report.ReportView
import com.rodrigoloq.watupakuy.feature.selector.SelectorView

@Composable
fun AppNavigation(modifier: Modifier) {
    val navController = rememberNavController()
    val startDestination = "main"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(startDestination = "selector", route = "main") {
            composable("selector") {
                SelectorView(navController = navController, modifier = modifier) }
            composable(
                "report" + "/{department}" + "/{date}",
                arguments = listOf(
                    navArgument(name = "department") { type = NavType.StringType },
                    navArgument(name = "date") { type = NavType.StringType })
            ) {backStackEntry ->
                val department = backStackEntry.arguments?.getString("department").orEmpty()
                val date = backStackEntry.arguments?.getString("date").orEmpty()
                ReportView(region = department,
                    yearMonth = date,
                    navController = navController,
                    modifier = modifier)
            }
        }
    }
}