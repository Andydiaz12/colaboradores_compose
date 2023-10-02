package com.upaep.upaeppersonal.viewmodel.features.menu

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upaep.upaeppersonal.model.entities.features.menu.MenuElements
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor() : ViewModel() {

    val contentOptions = mutableStateListOf<MenuElements>()

    init {
        contentOptions.addAll(contentOptions())
    }

    private fun contentOptions(): List<MenuElements> {
        return listOf(
            MenuElements(name = "Acerca de esta app", action = {}),
            MenuElements(name = "Aviso de privacidad", action = {}),
            MenuElements(name = "Reporta un problema", action = {}),
            MenuElements(name = "Recomienda la app", action = {}),
            MenuElements(name = "Normatividad para colaboradores", action = {}),
            MenuElements(name = "Manual de seguridad interna", action = {}),
            MenuElements(name = "Cerrar sesión", action = {})
        )
    }
}