package com.upaep.upaeppersonal.model.entities.features.menu

data class MenuElements(
    val name: String,
    val action: () -> Unit
)
