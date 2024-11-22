package dev.rustybite.rustygram.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.remote.FirebaseService;
import dev.rustybite.rustygram.data.repository.StorageRepository;
import io.github.jan.supabase.SupabaseClient;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class RustyGramModule_ProvidesUploadRepositoryFactory implements Factory<StorageRepository> {
  private final Provider<SupabaseClient> supabaseClientProvider;

  private final Provider<FirebaseService> firebaseServiceProvider;

  public RustyGramModule_ProvidesUploadRepositoryFactory(
      Provider<SupabaseClient> supabaseClientProvider,
      Provider<FirebaseService> firebaseServiceProvider) {
    this.supabaseClientProvider = supabaseClientProvider;
    this.firebaseServiceProvider = firebaseServiceProvider;
  }

  @Override
  public StorageRepository get() {
    return providesUploadRepository(supabaseClientProvider.get(), firebaseServiceProvider.get());
  }

  public static RustyGramModule_ProvidesUploadRepositoryFactory create(
      Provider<SupabaseClient> supabaseClientProvider,
      Provider<FirebaseService> firebaseServiceProvider) {
    return new RustyGramModule_ProvidesUploadRepositoryFactory(supabaseClientProvider, firebaseServiceProvider);
  }

  public static StorageRepository providesUploadRepository(SupabaseClient supabaseClient,
      FirebaseService firebaseService) {
    return Preconditions.checkNotNullFromProvides(RustyGramModule.INSTANCE.providesUploadRepository(supabaseClient, firebaseService));
  }
}
