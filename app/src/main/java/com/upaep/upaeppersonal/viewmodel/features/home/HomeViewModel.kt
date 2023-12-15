package com.upaep.upaeppersonal.viewmodel.features.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.model.entities.features.home.Feature
import com.upaep.upaeppersonal.model.entities.features.home.FeaturesRequest
import com.upaep.upaeppersonal.model.entities.features.upress.UpressItem
import com.upaep.upaeppersonal.model.repository.HomeRepository
import com.upaep.upaeppersonal.view.base.navigation.Routes
import com.upaep.upaeppersonal.viewmodel.features.upress.UpressData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.*

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    val news = mutableStateListOf<UpressItem>()
    val features = mutableStateListOf<Feature>()

    private val _loadingScreen = MutableLiveData(true)
    val loadingScreen: LiveData<Boolean> = _loadingScreen

    init {

        viewModelScope.launch {
            val homeFeatures = homeRepository.getFeatures(FeaturesRequest())
            _loadingScreen.value = false
            viewModelScope.launch {
                val homeNews = homeRepository.getNews()
                if (!homeNews.error) { news.addAll(homeNews.data!!) }
            }
            if(!homeFeatures.error) {
                features.addAll(homeFeatures.data!!)
                features.sortedBy { it.order }
            }
        }
    }

    fun navigateToNews(navigation: NavHostController?, clickedNew: UpressItem) {
        UpressData.setData(clickedNew)
        navigation?.navigate(Routes.UpressDescScreen.routes) { launchSingleTop = true }
    }
}