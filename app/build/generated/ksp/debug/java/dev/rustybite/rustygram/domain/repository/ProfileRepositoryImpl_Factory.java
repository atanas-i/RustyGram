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
public final class ProfileRepositoryImpl_Factory implements Factory<ProfileRepositoryImpl> {
  private final Provider<RustyGramService> serviceProvider;

  private final Provider<Retrofit> retrofitProvider;

  private final Provider<ResourceProvider> resProvider;

  public ProfileRepositoryImpl_Factory(Provider<RustyGramService> serviceProvider,
      Provider<Retrofit> retrofitProvider, Provider<ResourceProvider> resProvider) {
    this.serviceProvider = serviceProvider;
    this.retrofitProvider = retrofitProvider;
    this.resProvider = resProvider;
  }

  @Override
  public ProfileRepositoryImpl get() {
    return newInstance(serviceProvider.get(), retrofitProvider.get(), resProvider.get());
  }

  public static ProfileRepositoryImpl_Factory create(Provider<RustyGramService> serviceProvider,
      Provider<Retrofit> retrofitProvider, Provider<ResourceProvider> resProvider) {
    return new ProfileRepositoryImpl_Factory(serviceProvider, retrofitProvider, resProvider);
  }

  public static ProfileRepositoryImpl newInstance(RustyGramService service, Retrofit retrofit,
      ResourceProvider resProvider) {
    return new ProfileRepositoryImpl(service, retrofit, resProvider);
  }
}
