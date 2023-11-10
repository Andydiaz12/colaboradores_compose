package com.upaep.upaeppersonal.viewmodel.features.upress

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upaep.upaeppersonal.model.base.encryption.Base64Helper
import com.upaep.upaeppersonal.model.entities.features.upress.UpressItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpressDescriptionViewModel @Inject constructor() : ViewModel() {
    private val _item = MutableLiveData<UpressItem>()
    val item: LiveData<UpressItem> = _item

    init {
        _item.value = UpressData.getData()
        _item.value!!.introtext = Base64Helper.decodeBase64(item.value!!.introtext ?: "")
    }

    fun shareUpress(context: Context) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                _item.value!!.introtext
            )
            type = "text/html"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }
}