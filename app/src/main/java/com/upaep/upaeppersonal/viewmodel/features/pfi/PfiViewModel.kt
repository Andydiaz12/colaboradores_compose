package com.upaep.upaeppersonal.viewmodel.features.pfi

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.model.entities.features.pfi.PfiCategories
import com.upaep.upaeppersonal.model.repository.PfiRepository
import com.upaep.upaeppersonal.view.base.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PfiViewModel @Inject constructor(pfiRepository: PfiRepository) : ViewModel() {

    val categories = mutableStateListOf<PfiCategories>()

    init {
        viewModelScope.launch {
            val response = pfiRepository.getPfiCategories()
            if (!response.error) {
                categories.addAll(response.data!!)
            }
        }
    }

    fun navigateToPfiCategories(category: PfiCategories, navigation: NavHostController?) {
        PfiHelper(categoryId = category.categoryid, categoryName = category.name)
        navigation?.navigate(Routes.PfiByCategoryScreen.routes) {
            launchSingleTop = true
        }
    }
}