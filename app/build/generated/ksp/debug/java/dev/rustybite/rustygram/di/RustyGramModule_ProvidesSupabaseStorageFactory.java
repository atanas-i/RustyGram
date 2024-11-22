package dev.rustybite.rustygram.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.github.jan.supabase.SupabaseClient;
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
public final class RustyGramModule_ProvidesSupabaseStorageFactory implements Factory<SupabaseClient> {
  @Override
  public SupabaseClient get() {
    return providesSupabaseStorage();
  }

  public static RustyGramModule_ProvidesSupabaseStorageFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SupabaseClient providesSupabaseStorage() {
    return Preconditions.checkNotNullFromProvides(RustyGramModule.INSTANCE.providesSupabaseStorage());
  }

  private static final class InstanceHolder {
    private static final RustyGramModule_ProvidesSupabaseStorageFactory INSTANCE = new RustyGramModule_ProvidesSupabaseStorageFactory();
  }
}
