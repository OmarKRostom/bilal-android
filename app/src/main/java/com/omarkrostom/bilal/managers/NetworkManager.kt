package com.omarkrostom.bilal.managers

class NetworkManager {

    sealed class NetworkResource {
        object Loading: NetworkResource()
        data class Success<T>(val data: T)
        data class Failure(val data: NetworkError)
    }

    data class NetworkError(
        val errorMessage: String?,
        val statusCode: Int?
    ) {
        val networkErrorState: NetworkErrorStates by lazy {
            when(statusCode) {
                401 -> NetworkErrorStates.UnAuthorizedRequest
                400 -> NetworkErrorStates.BadRequest
                500 -> NetworkErrorStates.ServiceDownRequest
                else -> NetworkErrorStates.NoInternetConnection
            }
        }
    }

    sealed class NetworkErrorStates {
        object NoInternetConnection: NetworkErrorStates()
        object UnAuthorizedRequest: NetworkErrorStates()
        object BadRequest: NetworkErrorStates()
        object ServiceDownRequest: NetworkErrorStates()
    }

}