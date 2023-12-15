package com.upaep.upaeppersonal.model.entities.features.menu

import androidx.navigation.NavHostController

data class MenuElements(
    val name: String,
    val action: (NavHostController?) -> Unit
)
