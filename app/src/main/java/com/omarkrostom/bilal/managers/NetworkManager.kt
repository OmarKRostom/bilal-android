package com.omarkrostom.bilal.managers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.omarkrostom.bilal.BuildConfig
import com.omarkrostom.bilal.azan.BilalApi
import com.omarkrostom.bilal.masjidFinder.GooglePlacesApi
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkManager {

    @Provides
    fun providesNetworkClient(): OkHttpClient = OkHttpClient.Builder()
        .build()

    @Provides
    fun providesRestfulBuilder(): Retrofit.Builder = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    @Provides
    fun providesGooglePlacesApi(retrofitBuilder: Retrofit.Builder): GooglePlacesApi =
        retrofitBuilder.baseUrl(BuildConfig.PLACES_API).build().create(GooglePlacesApi::class.java)

    @Provides
    fun providesBilalApi(retrofitBuilder: Retrofit.Builder): BilalApi =
        retrofitBuilder.baseUrl(BuildConfig.BILAL_API).build().create(BilalApi::class.java)

    /**
     * Helper function to convert a single emit from Rx into LiveData
     */
    fun <T> Single<Response<T>>.create(): LiveData<NetworkResource<T>> {
        val responseLiveData = MutableLiveData<NetworkResource<T>>().apply {
            value = NetworkResource.Loading()
        }

        val disposable = this.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                responseLiveData.value = when (it.isSuccessful) {
                    true -> NetworkResource.Success(response = it.body())
                    else -> NetworkResource.Failure(error = NetworkError(it.message(), it.code()))
                }
            }, {
                responseLiveData.value =
                    NetworkResource.Failure(error = NetworkError(it.localizedMessage))
            })

        return responseLiveData.apply {
            disposable.dispose()
        }
    }

    sealed class NetworkResource<T>(
        val state: NetworkState,
        val data: T? = null,
        val networkError: NetworkError? = null
    ) {
        data class Loading<T>(val networkState: NetworkState = NetworkState.LOADING) :
            NetworkResource<T>(networkState)

        data class Success<T>(
            val networkState: NetworkState = NetworkState.SUCCESS,
            val response: T?
        ) : NetworkResource<T>(networkState, response)

        data class Failure<T>(
            val networkState: NetworkState = NetworkState.FAILURE,
            val error: NetworkError
        ) : NetworkResource<T>(networkState, networkError = error)
    }

    data class NetworkError(
        val errorMessage: String?,
        val statusCode: Int? = null
    ) {
        val networkErrorState: NetworkErrorStates by lazy {
            when (statusCode) {
                401 -> NetworkErrorStates.UnAuthorizedRequest
                400 -> NetworkErrorStates.BadRequest
                500 -> NetworkErrorStates.ServiceDownRequest
                else -> NetworkErrorStates.NoInternetConnection
            }
        }
    }

    sealed class NetworkState {
        object SUCCESS : NetworkState()
        object FAILURE : NetworkState()
        object LOADING : NetworkState()
    }

    sealed class NetworkErrorStates {
        object NoInternetConnection : NetworkErrorStates()
        object UnAuthorizedRequest : NetworkErrorStates()
        object BadRequest : NetworkErrorStates()
        object ServiceDownRequest : NetworkErrorStates()
    }

}