package com.upaep.upaeppersonal.viewmodel.features.token

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor() : ViewModel() {

    private lateinit var _imei: String

    @SuppressLint("HardwareIds")
    fun getImei(context: Context) {
        val phoneId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        Log.i("imeiDebug", phoneId)
//        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//        _imei = telephonyManager.
//        Log.i("imei", _imei)
    }
}

