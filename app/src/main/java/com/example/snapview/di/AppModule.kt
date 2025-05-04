package com.example.snapview.di

import android.content.Context
import com.example.snapview.data.remote.UnsplashAPIService
import com.example.snapview.data.repository.DownloaderImpl
import com.example.snapview.data.repository.ImageRepositoryImpl
import com.example.snapview.data.repository.NetworkConnectivityObserverImpl
import com.example.snapview.data.util.Constants.BASE_URL
import com.example.snapview.domain.repository.Downloader
import com.example.snapview.domain.repository.ImageRepository
import com.example.snapview.domain.repository.NetworkConnectivityObserver
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //we will create the objects of the unsplash api - apiService
    //this will return the value to the provide image repo
    @Provides
    @Singleton
    fun provideUnsplashAPIservice(): UnsplashAPIService {
        //it will ignore that unnecessary fields we deleted
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }

        //retrofit objects
        val retrofit = Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(UnsplashAPIService::class.java)
    }

    //We are telling the hilt that it need to see this function(repository)
    @Provides
    @Singleton
    //connecting the viewmodel with the repo
    fun provideImageRepository(apiService: UnsplashAPIService): ImageRepository {
        return ImageRepositoryImpl(apiService)
    }

    //we are attaching the downloader impl to the downloader interface
    @Provides
    @Singleton
    fun provideDownloader(@ApplicationContext context: Context): Downloader {
        return DownloaderImpl(context = context)
    }

    //provide function for that scope - scope
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    //binding our implementation
    @Provides
    @Singleton
    fun provideNetworkConnectivity(
        @ApplicationContext context: Context,
        scope: CoroutineScope
    ): NetworkConnectivityObserver {
        return NetworkConnectivityObserverImpl(
            context = context,
            scope = scope
        )
    }
}