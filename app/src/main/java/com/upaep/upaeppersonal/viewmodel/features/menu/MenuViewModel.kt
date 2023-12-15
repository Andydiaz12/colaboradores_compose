package com.upaep.upaeppersonal.viewmodel.features.menu

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.upaeppersonal.model.entities.features.menu.MenuElements
import com.upaep.upaeppersonal.model.repository.MenuRepository
import com.upaep.upaeppersonal.view.base.navigation.NavigationHelper
import com.upaep.upaeppersonal.view.base.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val navigationHelper: NavigationHelper
) : ViewModel() {

    val contentOptions = mutableStateListOf<MenuElements>()

    init {
        contentOptions.addAll(contentOptions())
    }

    private fun contentOptions(): List<MenuElements> {
        return listOf(
            MenuElements(name = "Acerca de esta app", action = {}),
            MenuElements(name = "Aviso de privacidad", action = { navigation ->
                navigationHelper(navigation = navigation, route = Routes.PrivacyPolicyScreen.routes)
            }),
            MenuElements(name = "Reporta un problema", action = {}),
            MenuElements(name = "Recomienda la app", action = {}),
            MenuElements(name = "Normatividad para colaboradores", action = {}),
            MenuElements(name = "Manual de seguridad interna", action = {}),
            MenuElements(name = "Cerrar sesión", action = {})
        )
    }
}