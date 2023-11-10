package com.upaep.upaeppersonal.model.base.exceptions

import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ExceptionHandler @Inject constructor() {
    operator fun invoke(e: Exception): String {
        return when (e) {

            is SocketTimeoutException -> {
                "No podemos acceder a la información debido a una falla en el servicio\n" +
                        "Inténtalo de nuevo más tarde o verifica tu conexión a internet."
            }

            is IOException -> {
                "Sin conexión a internet."
            }

            else -> {
                "No podemos acceder a la información debido a una falla en el servicio\n" +
                        "Inténtalo de nuevo más tarde o verifica tu conexión a internet."
            }
        }
    }
}