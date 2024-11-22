package dev.rustybite.rustygram.di;

import com.google.firebase.storage.FirebaseStorage;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.remote.FirebaseService;
import dev.rustybite.rustygram.util.ResourceProvider;
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
public final class RustyGramModule_ProvidesFirebaseServiceFactory implements Factory<FirebaseService> {
  private final Provider<FirebaseStorage> storageProvider;

  private final Provider<ResourceProvider> resourcesProvider;

  public RustyGramModule_ProvidesFirebaseServiceFactory(Provider<FirebaseStorage> storageProvider,
      Provider<ResourceProvider> resourcesProvider) {
    this.storageProvider = storageProvider;
    this.resourcesProvider = resourcesProvider;
  }

  @Override
  public FirebaseService get() {
    return providesFirebaseService(storageProvider.get(), resourcesProvider.get());
  }

  public static RustyGramModule_ProvidesFirebaseServiceFactory create(
      Provider<FirebaseStorage> storageProvider, Provider<ResourceProvider> resourcesProvider) {
    return new RustyGramModule_ProvidesFirebaseServiceFactory(storageProvider, resourcesProvider);
  }

  public static FirebaseService providesFirebaseService(FirebaseStorage storage,
      ResourceProvider resources) {
    return Preconditions.checkNotNullFromProvides(RustyGramModule.INSTANCE.providesFirebaseService(storage, resources));
  }
}
