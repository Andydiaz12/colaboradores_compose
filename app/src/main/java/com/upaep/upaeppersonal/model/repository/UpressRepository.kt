package com.upaep.upaeppersonal.model.repository

import com.upaep.upaeppersonal.model.api.features.UpressService
import com.upaep.upaeppersonal.model.entities.features.upress.UpressItem
import javax.inject.Inject

class UpressRepository @Inject constructor(private val upressService: UpressService) {
    suspend fun getUpressContent(): List<UpressItem> {
        return upressService.getUpressContent()
    }
}