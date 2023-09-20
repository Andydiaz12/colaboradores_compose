package com.upaep.upaeppersonal.viewmodel.features.credential

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrScreenViewModel @Inject constructor() : ViewModel() {
    private lateinit var _navigation: NavHostController
    private lateinit var _activity: Activity
    private var _counter = 60
    private val _timer = MutableLiveData("01:00")
    val timer: LiveData<String> = _timer

    init {
        viewModelScope.launch {
            while (_counter != 0) {
                delay(1000)
                _counter--
                _timer.value = "00:${
                    if (_counter > 9)
                        _counter
                    else "0$_counter"
                }"
            }
            delay(1000)
            _navigation.popBackStack()
        }
    }

    fun setNavigation(navigation: NavHostController, context: Context) {
        _navigation = navigation
        _activity = context as Activity
        setBrightness()
    }


    fun setBrightness() {
        _activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val params = _activity.window.attributes
        Log.i("paramsDebug", params.screenBrightness.toString())
//        params.screenBrightness = 1.0f
        params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL
        _activity.window.attributes = params
    }

    fun resetBrightness() {
        val params = _activity.window.attributes

        // Devuelve el control del brillo al sistema
        params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
        _activity.window.attributes = params
    }
}