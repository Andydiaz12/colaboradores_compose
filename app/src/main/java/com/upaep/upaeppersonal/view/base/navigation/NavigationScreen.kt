package com.upaep.upaeppersonal.view.base.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upaep.upaeppersonal.view.features.assistance.AssistanceScreen
import com.upaep.upaeppersonal.view.features.benefits.BenefitsScreen
import com.upaep.upaeppersonal.view.features.credential.CredentialScreen
import com.upaep.upaeppersonal.view.features.credential.QrScreen
import com.upaep.upaeppersonal.view.features.home.HomeScreen
import com.upaep.upaeppersonal.view.features.library.LibraryScreen
import com.upaep.upaeppersonal.view.features.login.LoginScreen
import com.upaep.upaeppersonal.view.features.menu.MenuScreen
import com.upaep.upaeppersonal.view.features.parku.ParkUScreen
import com.upaep.upaeppersonal.view.features.schedule.ScheduleScreen
import com.upaep.upaeppersonal.view.features.token.TokenScreen

@Composable
fun AppNavigation() {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = Routes.HomeScreen.routes) {
        composable(Routes.LoginScreen.routes) {
            LoginScreen()
        }
        composable(Routes.HomeScreen.routes) {
            HomeScreen(navigation = navigationController)
        }
        composable(Routes.MenuScreen.routes) {
            MenuScreen(navigation = navigationController)
        }
        composable(Routes.LibraryScreen.routes) {
            LibraryScreen(navigation = navigationController)
        }
        composable(Routes.CredentialScreen.routes) {
            CredentialScreen(navigation = navigationController)
        }
        composable(Routes.QrScreen.routes) {
            QrScreen(navigation = navigationController)
        }
        composable(Routes.AssistanceScreen.routes) {
            AssistanceScreen()
        }
        composable(Routes.BenefitsScreen.routes) {
            BenefitsScreen()
        }
        composable(Routes.ParkUScreen.routes) {
            ParkUScreen()
        }
        composable(Routes.ScheduleScreen.routes) {
            ScheduleScreen()
        }
        composable(Routes.TokenScreen.routes) {
            TokenScreen()
        }
    }
}