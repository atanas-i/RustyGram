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
public final class UserRepositoryImpl_Factory implements Factory<UserRepositoryImpl> {
  private final Provider<RustyGramService> serviceProvider;

  private final Provider<ResourceProvider> resourcesProvider;

  private final Provider<Retrofit> retrofitProvider;

  public UserRepositoryImpl_Factory(Provider<RustyGramService> serviceProvider,
      Provider<ResourceProvider> resourcesProvider, Provider<Retrofit> retrofitProvider) {
    this.serviceProvider = serviceProvider;
    this.resourcesProvider = resourcesProvider;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public UserRepositoryImpl get() {
    return newInstance(serviceProvider.get(), resourcesProvider.get(), retrofitProvider.get());
  }

  public static UserRepositoryImpl_Factory create(Provider<RustyGramService> serviceProvider,
      Provider<ResourceProvider> resourcesProvider, Provider<Retrofit> retrofitProvider) {
    return new UserRepositoryImpl_Factory(serviceProvider, resourcesProvider, retrofitProvider);
  }

  public static UserRepositoryImpl newInstance(RustyGramService service, ResourceProvider resources,
      Retrofit retrofit) {
    return new UserRepositoryImpl(service, resources, retrofit);
  }
}
