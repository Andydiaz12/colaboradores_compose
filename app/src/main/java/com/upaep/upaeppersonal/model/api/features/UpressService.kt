package com.upaep.upaeppersonal.model.api.features

import android.util.Log
import com.upaep.upaeppersonal.model.base.ColaboradoresInterface
import com.upaep.upaeppersonal.model.entities.features.upress.UpressItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpressService @Inject constructor(private val api: ColaboradoresInterface) {
    suspend fun getUpressContent(): List<UpressItem> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.testUpressNews()
                if (response.isSuccessful) response.body()!! else emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}