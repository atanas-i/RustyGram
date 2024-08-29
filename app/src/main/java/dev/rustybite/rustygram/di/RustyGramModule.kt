package dev.rustybite.rustygram.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.rustybite.rustygram.data.local.SessionManager
import dev.rustybite.rustygram.data.remote.RustyGramService
import dev.rustybite.rustygram.data.repository.LoginRepository
import dev.rustybite.rustygram.data.repository.ProfileRepository
import dev.rustybite.rustygram.data.repository.StorageRepository
import dev.rustybite.rustygram.data.repository.TokenManagementRepository
import dev.rustybite.rustygram.data.repository.UserRegistrationRepository
import dev.rustybite.rustygram.domain.local.SessionManagerImpl
import dev.rustybite.rustygram.domain.repository.LoginRepositoryImpl
import dev.rustybite.rustygram.domain.repository.ProfileRepositoryImpl
import dev.rustybite.rustygram.domain.repository.StorageRepositoryImpl
import dev.rustybite.rustygram.domain.repository.TokenManagementRepositoryImpl
import dev.rustybite.rustygram.domain.repository.UserRegistrationRepositoryImpl
import dev.rustybite.rustygram.util.API_KEY
import dev.rustybite.rustygram.util.BASE_URL
import dev.rustybite.rustygram.util.ResourceProvider
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RustyGramModule {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): RustyGramService = retrofit.create(RustyGramService::class.java)

    @Provides
    @Singleton
    fun provideRegistrationRepository(service: RustyGramService, retrofit: Retrofit, resources: ResourceProvider): UserRegistrationRepository =
        UserRegistrationRepositoryImpl(service, retrofit, resources)

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider =
        ResourceProvider(context)

    @Provides
    @Singleton
    fun providesLoginRepository(service: RustyGramService, retrofit: Retrofit, resources: ResourceProvider): LoginRepository =
        LoginRepositoryImpl(service, retrofit, resources)

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager =
        SessionManagerImpl(context)

    @Provides
    @Singleton
    fun providesTokenManagementRepository(service: RustyGramService, retrofit: Retrofit, resources: ResourceProvider): TokenManagementRepository =
        TokenManagementRepositoryImpl(service, retrofit, resources)

    @Provides
    @Singleton
    fun providesSupabaseStorage(): SupabaseClient = createSupabaseClient(
        BASE_URL,
        API_KEY,
    ) {
        install(Storage)
    }

    @Provides
    @Singleton
    fun providesUploadRepository(supabaseClient: SupabaseClient): StorageRepository =
        StorageRepositoryImpl(supabaseClient)

    @Provides
    @Singleton
    fun providesProfileRepository(service: RustyGramService, retrofit: Retrofit, resources: ResourceProvider): ProfileRepository =
        ProfileRepositoryImpl(service, retrofit, resources)



}