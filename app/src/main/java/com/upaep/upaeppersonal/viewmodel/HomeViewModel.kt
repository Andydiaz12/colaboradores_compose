package com.upaep.upaeppersonal.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.upaeppersonal.model.repository.HomeRepository
import com.upaep.upaeppersonal.view.base.ScreenStates
import com.upaep.upaeppersonal.view.base.ScreenStates.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

//    val uiState: StateFlow<ScreenStates> = homeRepository.test.map(::Success)
//        .catch { Error(it) }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    private val _uiState : MutableStateFlow<ScreenStates> = MutableStateFlow(Loading)
    val uiState: StateFlow<ScreenStates> = _uiState

//    init {
//        viewModelScope.launch {
//            homeRepository.test.collect {
//                _uiState.value = if(it.data.isEmpty()) EmptyData else Success(data = it.data)
//                Log.i("debugTest", it.toString())
//            }
//        }
//    }


//    fun agregarRandom() {
//        val test =
//            Test(name = generarCadenaAleatoria(5), extra = Random.Default.nextInt(10).toString())
//        viewModelScope.launch {
//            homeRepository.addTest(test)
//        }
//    }
//
//    fun deleteData() {
//        viewModelScope.launch {
//            homeRepository.deleteData()
//        }
//    }

    private fun generarCadenaAleatoria(longitud: Int): String {
        val caracteres = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random.Default
        val sb = StringBuilder(longitud)

        repeat(longitud) {
            val indice = random.nextInt(caracteres.length)
            val caracterAleatorio = caracteres[indice]
            sb.append(caracterAleatorio)
        }

        return sb.toString()
    }
}