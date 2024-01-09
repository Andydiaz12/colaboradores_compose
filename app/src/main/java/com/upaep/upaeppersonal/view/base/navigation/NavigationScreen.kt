package com.upaep.upaeppersonal.view.base.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upaep.upaeppersonal.view.features.assistance.AssistanceClassScreen
import com.upaep.upaeppersonal.view.features.assistance.AssistanceScreen
import com.upaep.upaeppersonal.view.features.benefits.BenefitsByCategoryScreen
import com.upaep.upaeppersonal.view.features.benefits.BenefitsScreen
import com.upaep.upaeppersonal.view.features.calendar.CalendarScreen
import com.upaep.upaeppersonal.view.features.credential.CredentialScreen
import com.upaep.upaeppersonal.view.features.credential.QrScreen
import com.upaep.upaeppersonal.view.features.dailymail.DailyMailByCategoryScreen
import com.upaep.upaeppersonal.view.features.dailymail.DailyMailDescriptionScreen
import com.upaep.upaeppersonal.view.features.dailymail.DailyMailScreen
import com.upaep.upaeppersonal.view.features.directory.DirectoryScreen
import com.upaep.upaeppersonal.view.features.events.EventsScreen
import com.upaep.upaeppersonal.view.features.home.HomeScreen
import com.upaep.upaeppersonal.view.features.library.BooksListScreen
import com.upaep.upaeppersonal.view.features.library.LibraryScreen
import com.upaep.upaeppersonal.view.features.login.LoginScreen
import com.upaep.upaeppersonal.view.features.mailbox.MailboxScreen
import com.upaep.upaeppersonal.view.features.menu.MenuScreen
import com.upaep.upaeppersonal.view.features.menu.PrivacyScreen
import com.upaep.upaeppersonal.view.features.parku.ParkUScreen
import com.upaep.upaeppersonal.view.features.pfi.PfiByCategoryScreen
import com.upaep.upaeppersonal.view.features.pfi.PfiScreen
import com.upaep.upaeppersonal.view.features.schedule.ScheduleScreen
import com.upaep.upaeppersonal.view.features.token.TokenScreen
import com.upaep.upaeppersonal.view.features.upress.UpressDescriptionScreen
import com.upaep.upaeppersonal.view.features.upress.UpressScreen

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
        composable(Routes.BooksListScreen.routes) {
            BooksListScreen()
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
        composable(Routes.AssistanceClassScreen.routes) {
            AssistanceClassScreen()
        }
        composable(Routes.AssistanceScreen.routes) {
            AssistanceScreen()
        }
        composable(Routes.BenefitsScreen.routes) {
            BenefitsScreen(navigation = navigationController)
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
        composable(Routes.MailboxScreen.routes) {
            MailboxScreen()
        }
        composable(Routes.DirectoryScreen.routes) {
            DirectoryScreen()
        }
        composable(Routes.CalendarScreen.routes) {
            CalendarScreen()
        }
        composable(Routes.UpressScreen.routes) {
            UpressScreen(navigation = navigationController)
        }
        composable(Routes.UpressDescScreen.routes) {
            UpressDescriptionScreen()
        }
        composable(Routes.EventsScreen.routes) {
            EventsScreen()
        }
        composable(Routes.PrivacyPolicyScreen.routes) {
            PrivacyScreen(navigation = navigationController)
        }
        composable(Routes.BenefitsByCategoryScreen.routes) {
            BenefitsByCategoryScreen(navigation = navigationController)
        }
        composable(Routes.PfiScreen.routes) {
            PfiScreen(navigation = navigationController)
        }
        composable(Routes.PfiByCategoryScreen.routes) {
            PfiByCategoryScreen()
        }
        composable(Routes.DailyMailScreen.routes) {
            DailyMailScreen(navigation = navigationController)
        }
        composable(Routes.DailyMailByCategoryScreen.routes) {
            DailyMailByCategoryScreen(navigation = navigationController)
        }
        composable(Routes.DailyMailDescriptionScreen.routes) {
            DailyMailDescriptionScreen(navigation = navigationController)
        }
    }
}