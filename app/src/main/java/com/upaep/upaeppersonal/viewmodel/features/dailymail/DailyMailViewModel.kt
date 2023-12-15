package com.upaep.upaeppersonal.viewmodel.features.dailymail

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.model.entities.features.dailymail.DailyMailCategories
import com.upaep.upaeppersonal.model.repository.DailyMailRepository
import com.upaep.upaeppersonal.view.base.navigation.NavigationHelper
import com.upaep.upaeppersonal.view.base.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyMailViewModel @Inject constructor(
    dailyMailRepository: DailyMailRepository,
    private val navigationHelper: NavigationHelper
) : ViewModel() {

    val categories = mutableStateListOf<DailyMailCategories>()

    init {
        viewModelScope.launch {
            val categoriesResponse = dailyMailRepository.getMailCategories()
            if (!categoriesResponse.error) {
                categories.addAll(categoriesResponse.data!!)
            }
        }
    }

    fun navigationToCategory(categoryId: Int, navigation: NavHostController?) {
        DailyMailHelper(categoryId = categoryId)
        navigationHelper(navigation = navigation, route = Routes.DailyMailByCategoryScreen.routes)
    }
}