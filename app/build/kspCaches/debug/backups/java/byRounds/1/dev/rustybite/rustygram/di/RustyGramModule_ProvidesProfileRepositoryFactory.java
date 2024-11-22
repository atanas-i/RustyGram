package dev.rustybite.rustygram.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.remote.RustyGramService;
import dev.rustybite.rustygram.data.repository.ProfileRepository;
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
public final class RustyGramModule_ProvidesProfileRepositoryFactory implements Factory<ProfileRepository> {
  private final Provider<RustyGramService> serviceProvider;

  private final Provider<Retrofit> retrofitProvider;

  private final Provider<ResourceProvider> resourcesProvider;

  public RustyGramModule_ProvidesProfileRepositoryFactory(
      Provider<RustyGramService> serviceProvider, Provider<Retrofit> retrofitProvider,
      Provider<ResourceProvider> resourcesProvider) {
    this.serviceProvider = serviceProvider;
    this.retrofitProvider = retrofitProvider;
    this.resourcesProvider = resourcesProvider;
  }

  @Override
  public ProfileRepository get() {
    return providesProfileRepository(serviceProvider.get(), retrofitProvider.get(), resourcesProvider.get());
  }

  public static RustyGramModule_ProvidesProfileRepositoryFactory create(
      Provider<RustyGramService> serviceProvider, Provider<Retrofit> retrofitProvider,
      Provider<ResourceProvider> resourcesProvider) {
    return new RustyGramModule_ProvidesProfileRepositoryFactory(serviceProvider, retrofitProvider, resourcesProvider);
  }

  public static ProfileRepository providesProfileRepository(RustyGramService service,
      Retrofit retrofit, ResourceProvider resources) {
    return Preconditions.checkNotNullFromProvides(RustyGramModule.INSTANCE.providesProfileRepository(service, retrofit, resources));
  }
}
