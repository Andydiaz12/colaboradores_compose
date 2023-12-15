package com.upaep.upaeppersonal.view.base.navigation

import androidx.navigation.NavHostController
import javax.inject.Inject

class NavigationHelper @Inject constructor() {
    operator fun invoke(navigation: NavHostController?, route: String) {
        navigation?.navigate(route) { launchSingleTop = true }
    }
}