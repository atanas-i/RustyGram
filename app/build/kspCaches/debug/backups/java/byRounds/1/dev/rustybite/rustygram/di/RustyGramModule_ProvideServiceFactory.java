package dev.rustybite.rustygram.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.remote.RustyGramService;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class RustyGramModule_ProvideServiceFactory implements Factory<RustyGramService> {
  private final Provider<Retrofit> retrofitProvider;

  public RustyGramModule_ProvideServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public RustyGramService get() {
    return provideService(retrofitProvider.get());
  }

  public static RustyGramModule_ProvideServiceFactory create(Provider<Retrofit> retrofitProvider) {
    return new RustyGramModule_ProvideServiceFactory(retrofitProvider);
  }

  public static RustyGramService provideService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(RustyGramModule.INSTANCE.provideService(retrofit));
  }
}
