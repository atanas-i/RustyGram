package dev.rustybite.rustygram.di;

import com.google.firebase.storage.FirebaseStorage;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class RustyGramModule_ProvidesFirebaseStorageFactory implements Factory<FirebaseStorage> {
  @Override
  public FirebaseStorage get() {
    return providesFirebaseStorage();
  }

  public static RustyGramModule_ProvidesFirebaseStorageFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FirebaseStorage providesFirebaseStorage() {
    return Preconditions.checkNotNullFromProvides(RustyGramModule.INSTANCE.providesFirebaseStorage());
  }

  private static final class InstanceHolder {
    private static final RustyGramModule_ProvidesFirebaseStorageFactory INSTANCE = new RustyGramModule_ProvidesFirebaseStorageFactory();
  }
}
