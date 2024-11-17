package dev.rustybite.rustygram.di;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.util.ResourceProvider;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class RustyGramModule_ProvideResourceProviderFactory implements Factory<ResourceProvider> {
  private final Provider<Context> contextProvider;

  public RustyGramModule_ProvideResourceProviderFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ResourceProvider get() {
    return provideResourceProvider(contextProvider.get());
  }

  public static RustyGramModule_ProvideResourceProviderFactory create(
      Provider<Context> contextProvider) {
    return new RustyGramModule_ProvideResourceProviderFactory(contextProvider);
  }

  public static ResourceProvider provideResourceProvider(Context context) {
    return Preconditions.checkNotNullFromProvides(RustyGramModule.INSTANCE.provideResourceProvider(context));
  }
}
