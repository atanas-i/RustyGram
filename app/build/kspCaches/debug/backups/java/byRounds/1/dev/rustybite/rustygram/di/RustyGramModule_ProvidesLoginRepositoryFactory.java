package dev.rustybite.rustygram.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.remote.RustyGramService;
import dev.rustybite.rustygram.data.repository.LoginRepository;
import dev.rustybite.rustygram.util.ResourceProvider;
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
public final class RustyGramModule_ProvidesLoginRepositoryFactory implements Factory<LoginRepository> {
  private final Provider<RustyGramService> serviceProvider;

  private final Provider<Retrofit> retrofitProvider;

  private final Provider<ResourceProvider> resourcesProvider;

  public RustyGramModule_ProvidesLoginRepositoryFactory(Provider<RustyGramService> serviceProvider,
      Provider<Retrofit> retrofitProvider, Provider<ResourceProvider> resourcesProvider) {
    this.serviceProvider = serviceProvider;
    this.retrofitProvider = retrofitProvider;
    this.resourcesProvider = resourcesProvider;
  }

  @Override
  public LoginRepository get() {
    return providesLoginRepository(serviceProvider.get(), retrofitProvider.get(), resourcesProvider.get());
  }

  public static RustyGramModule_ProvidesLoginRepositoryFactory create(
      Provider<RustyGramService> serviceProvider, Provider<Retrofit> retrofitProvider,
      Provider<ResourceProvider> resourcesProvider) {
    return new RustyGramModule_ProvidesLoginRepositoryFactory(serviceProvider, retrofitProvider, resourcesProvider);
  }

  public static LoginRepository providesLoginRepository(RustyGramService service, Retrofit retrofit,
      ResourceProvider resources) {
    return Preconditions.checkNotNullFromProvides(RustyGramModule.INSTANCE.providesLoginRepository(service, retrofit, resources));
  }
}
