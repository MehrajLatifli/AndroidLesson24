package com.example.turkishmuseums.dependencyInjection

import com.example.turkishmuseums.source.api.IApiManager
import com.example.turkishmuseums.utilities.Constants.Base_URL
import com.squareup.picasso.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {






    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Base_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideIApiManager(retrofit: Retrofit): IApiManager {
        return  retrofit.create(IApiManager::class.java)
    }


}