/*
 * Created by Elnur Hajiyev on on 7/27/22, 3:38 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.data.di

import android.content.Context
import android.content.res.AssetManager
import androidx.annotation.NonNull
import loan.calculator.data.entities.HostInfoConstants
import loan.calculator.data.entities.RetrofitHostConfig
import loan.calculator.data.repository.FileStorage
import loan.calculator.data.repository.FileStorageImpl
import loan.calculator.data.remote.api.HomeApi
import loan.calculator.data.remote.interceptor.AuthInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.InputStream
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHostConfig(@ApplicationContext context: Context): RetrofitHostConfig {
        val properties = Properties()
        val assetManager: AssetManager = context.assets
        val inputStream: InputStream = assetManager.open("retrofitclient.properties")
        properties.load(inputStream)
        val serverURL = properties.getProperty(HostInfoConstants.SERVER_URL_KEY)
        val serverPort = ""
        val connectTimeout = properties.getProperty(HostInfoConstants.CONNECT_TIMEOUT_KEY).toLong()
        val readTimeout = properties.getProperty(HostInfoConstants.READ_TIMEOUT_KEY).toLong()
        val mainSHAKey = properties.getProperty(HostInfoConstants.SERVER_SHA_MAIN)
        val backupSHAKey = properties.getProperty(HostInfoConstants.SERVER_SHA_BACKUP)
        return RetrofitHostConfig(
            serverURL,
            serverPort,
            readTimeout,
            connectTimeout,
            mainSHAKey,
            backupSHAKey
        )
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        retrofitHostConfig: RetrofitHostConfig,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(retrofitHostConfig.readTimeout, TimeUnit.SECONDS)
        httpClient.connectTimeout(retrofitHostConfig.connectTimeout, TimeUnit.SECONDS)
        httpClient.addInterceptor(httpLoggingInterceptor)
        httpClient.addInterceptor(authInterceptor)
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideKotlinJsonAdapterFactory(): KotlinJsonAdapterFactory = KotlinJsonAdapterFactory()

    @Provides
    @Singleton
    fun provideMoshi(kotlinJsonAdapterFactory: KotlinJsonAdapterFactory): Moshi = Moshi.Builder()
        .add(kotlinJsonAdapterFactory)
        .build()

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun provideRetrofit(
        @NonNull okHttpClient: OkHttpClient,
        retrofitHostConfig: RetrofitHostConfig,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        val serverUrl =
            "${retrofitHostConfig.baseUrl}${if (retrofitHostConfig.serverPort.isEmpty()) "" else ":${retrofitHostConfig.serverPort}"}/"
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(serverUrl)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

    @Provides
    @Singleton
    fun provideHomeApi(retrofit: Retrofit) = retrofit.create(HomeApi::class.java)

    @Provides
    @Singleton
    fun provideFileStorage(@ApplicationContext context: Context): FileStorage {
        return FileStorageImpl().apply {
            val inputStream: InputStream = context.assets.open("minioclient.properties")
            val properties = Properties().apply {
                load(inputStream)
            }
            url = properties.getProperty(HostInfoConstants.FILE_SERVER_URL_KEY)
        }
    }
}
