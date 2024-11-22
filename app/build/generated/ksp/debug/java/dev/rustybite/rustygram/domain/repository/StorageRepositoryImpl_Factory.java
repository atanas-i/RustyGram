package dev.rustybite.rustygram.domain.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.remote.FirebaseService;
import io.github.jan.supabase.SupabaseClient;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class StorageRepositoryImpl_Factory implements Factory<StorageRepositoryImpl> {
  private final Provider<SupabaseClient> supabaseProvider;

  private final Provider<FirebaseService> firebaseServiceProvider;

  public StorageRepositoryImpl_Factory(Provider<SupabaseClient> supabaseProvider,
      Provider<FirebaseService> firebaseServiceProvider) {
    this.supabaseProvider = supabaseProvider;
    this.firebaseServiceProvider = firebaseServiceProvider;
  }

  @Override
  public StorageRepositoryImpl get() {
    return newInstance(supabaseProvider.get(), firebaseServiceProvider.get());
  }

  public static StorageRepositoryImpl_Factory create(Provider<SupabaseClient> supabaseProvider,
      Provider<FirebaseService> firebaseServiceProvider) {
    return new StorageRepositoryImpl_Factory(supabaseProvider, firebaseServiceProvider);
  }

  public static StorageRepositoryImpl newInstance(SupabaseClient supabase,
      FirebaseService firebaseService) {
    return new StorageRepositoryImpl(supabase, firebaseService);
  }
}
