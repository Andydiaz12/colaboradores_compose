package com.upaep.upaeppersonal.model.entities.upaepservices

import com.google.gson.annotations.SerializedName

data class UpaepStandardResponse<T>(
    @SerializedName("CRYPTDATA")
    val data: T? = null,
    @SerializedName("statusCode")
    var statusCode: Int = 12163,
    @SerializedName("error")
    var error: Boolean = true,
    @SerializedName("message")
    var message: String = "No podemos acceder a la información debido a una falla en el servicio.\nInténtalo de nuevo más tarde o verifica tu conexión a internet."
)