package com.mora.minikeepsafe.dependencyinjection

import com.google.gson.Gson
import com.mora.minikeepsafe.data.IPhotoWebService
import com.mora.minikeepsafe.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn((SingletonComponent::class))
class RepositoryModule {

    @Singleton
    @Provides
    fun providesPhotoWebService() : IPhotoWebService {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.addInterceptor(loggingInterceptor)
        val client = okHttpClient.build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

        return retrofit.create(IPhotoWebService::class.java)
    }
}