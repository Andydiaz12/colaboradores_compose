package com.upaep.upaeppersonal.view.base

sealed interface ScreenStates {
    object Loading : ScreenStates
    data class Error(val throwable: Throwable) : ScreenStates
    data class Success<T>(val data: T) : ScreenStates
    object EmptyData : ScreenStates
}