package com.anil.newapp.persistance

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class InternalErrorConverterImpl : InternalErrorConverter {

    override fun convertToGeneralErrorType(error: Throwable): ErrorType {
        if (error is ConnectException || error is SocketTimeoutException || error is UnknownHostException) {
            return ErrorType.SERVER_CONNECTION
        }
        val errorMessage = error.message
        if (errorMessage.isNullOrEmpty()) {
            return ErrorType.SERVER_OTHER
        }
        return when (errorMessage) {
            ERROR_403 -> ErrorType.SERVER_INTERNAL
            else -> ErrorType.SERVER_OTHER
        }
    }

    companion object {
        private const val ERROR_403: String = "HTTP 403 "
    }

}