package dev.rustybite.rustygram.data.remote;

import com.google.firebase.storage.FirebaseStorage;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.util.ResourceProvider;
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
public final class FirebaseService_Factory implements Factory<FirebaseService> {
  private final Provider<FirebaseStorage> storageProvider;

  private final Provider<ResourceProvider> resProvider;

  public FirebaseService_Factory(Provider<FirebaseStorage> storageProvider,
      Provider<ResourceProvider> resProvider) {
    this.storageProvider = storageProvider;
    this.resProvider = resProvider;
  }

  @Override
  public FirebaseService get() {
    return newInstance(storageProvider.get(), resProvider.get());
  }

  public static FirebaseService_Factory create(Provider<FirebaseStorage> storageProvider,
      Provider<ResourceProvider> resProvider) {
    return new FirebaseService_Factory(storageProvider, resProvider);
  }

  public static FirebaseService newInstance(FirebaseStorage storage, ResourceProvider resProvider) {
    return new FirebaseService(storage, resProvider);
  }
}
