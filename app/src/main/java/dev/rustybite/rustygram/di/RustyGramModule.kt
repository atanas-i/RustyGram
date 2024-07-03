package dev.rustybite.rustygram.di

import android.provider.SyncStateContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.rustybite.rustygram.data.remote.RustyGramService
import dev.rustybite.rustygram.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RustyGramModule {


    @Provides
    @Singleton
    fun provideService(): RustyGramService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RustyGramService::class.java)
}