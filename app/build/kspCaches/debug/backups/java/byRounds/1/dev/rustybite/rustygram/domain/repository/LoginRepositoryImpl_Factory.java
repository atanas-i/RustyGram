package dev.rustybite.rustygram.domain.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.remote.RustyGramService;
import dev.rustybite.rustygram.util.ResourceProvider;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class LoginRepositoryImpl_Factory implements Factory<LoginRepositoryImpl> {
  private final Provider<RustyGramService> serviceProvider;

  private final Provider<Retrofit> retrofitProvider;

  private final Provider<ResourceProvider> resourcesProvider;

  public LoginRepositoryImpl_Factory(Provider<RustyGramService> serviceProvider,
      Provider<Retrofit> retrofitProvider, Provider<ResourceProvider> resourcesProvider) {
    this.serviceProvider = serviceProvider;
    this.retrofitProvider = retrofitProvider;
    this.resourcesProvider = resourcesProvider;
  }

  @Override
  public LoginRepositoryImpl get() {
    return newInstance(serviceProvider.get(), retrofitProvider.get(), resourcesProvider.get());
  }

  public static LoginRepositoryImpl_Factory create(Provider<RustyGramService> serviceProvider,
      Provider<Retrofit> retrofitProvider, Provider<ResourceProvider> resourcesProvider) {
    return new LoginRepositoryImpl_Factory(serviceProvider, retrofitProvider, resourcesProvider);
  }

  public static LoginRepositoryImpl newInstance(RustyGramService service, Retrofit retrofit,
      ResourceProvider resources) {
    return new LoginRepositoryImpl(service, retrofit, resources);
  }
}
