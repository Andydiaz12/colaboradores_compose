package com.upaep.upaeppersonal.viewmodel.features.upress

import com.upaep.upaeppersonal.model.entities.features.upress.UpressItem

object UpressData {
    private lateinit var upress: UpressItem

    fun setData(item: UpressItem) { this.upress = item }
    fun getData(): UpressItem = this.upress
}