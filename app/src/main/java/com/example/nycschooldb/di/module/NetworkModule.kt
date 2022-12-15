package com.example.nycschooldb.di.module

import android.net.ConnectivityManager
import com.example.nycschooldb.common.NetworkManager
import com.example.nycschooldb.common.Utils.Companion.BASE_URL
import com.example.nycschooldb.model.remote.NycApiService
import com.example.nycschooldb.repository.NycRepoImpl
import com.example.nycschooldb.repository.NycRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNycApi(retrofit: Retrofit): NycApiService{
        return retrofit.create(NycApiService::class.java)
    }


    @Provides
    fun provideNetworkManager(connectivityManager: ConnectivityManager):
            NetworkManager = NetworkManager(connectivityManager)

}