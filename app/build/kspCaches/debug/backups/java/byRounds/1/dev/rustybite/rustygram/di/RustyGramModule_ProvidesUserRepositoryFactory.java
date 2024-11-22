package dev.rustybite.rustygram.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.remote.RustyGramService;
import dev.rustybite.rustygram.data.repository.UserRepository;
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
public final class RustyGramModule_ProvidesUserRepositoryFactory implements Factory<UserRepository> {
  private final Provider<RustyGramService> serviceProvider;

  private final Provider<Retrofit> retrofitProvider;

  private final Provider<ResourceProvider> resProvider;

  public RustyGramModule_ProvidesUserRepositoryFactory(Provider<RustyGramService> serviceProvider,
      Provider<Retrofit> retrofitProvider, Provider<ResourceProvider> resProvider) {
    this.serviceProvider = serviceProvider;
    this.retrofitProvider = retrofitProvider;
    this.resProvider = resProvider;
  }

  @Override
  public UserRepository get() {
    return providesUserRepository(serviceProvider.get(), retrofitProvider.get(), resProvider.get());
  }

  public static RustyGramModule_ProvidesUserRepositoryFactory create(
      Provider<RustyGramService> serviceProvider, Provider<Retrofit> retrofitProvider,
      Provider<ResourceProvider> resProvider) {
    return new RustyGramModule_ProvidesUserRepositoryFactory(serviceProvider, retrofitProvider, resProvider);
  }

  public static UserRepository providesUserRepository(RustyGramService service, Retrofit retrofit,
      ResourceProvider resProvider) {
    return Preconditions.checkNotNullFromProvides(RustyGramModule.INSTANCE.providesUserRepository(service, retrofit, resProvider));
  }
}
