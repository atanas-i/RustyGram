package dev.rustybite.rustygram.di

import android.content.Context
import android.provider.SyncStateContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.rustybite.rustygram.data.remote.RustyGramService
import dev.rustybite.rustygram.data.repository.UserRegistrationRepository
import dev.rustybite.rustygram.domain.repository.UserRegistrationRepositoryImpl
import dev.rustybite.rustygram.util.BASE_URL
import dev.rustybite.rustygram.util.ResourceProvider
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

    @Provides
    @Singleton
    fun provideRepository(service: RustyGramService): UserRegistrationRepository = UserRegistrationRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider = ResourceProvider(context.resources)

}