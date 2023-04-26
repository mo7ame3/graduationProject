package com.example.graduationproject.di

import com.example.graduationproject.constant.Constant
import com.example.graduationproject.network.GraduationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideGraduationApi(): GraduationApi {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GraduationApi::class.java)
    }

}