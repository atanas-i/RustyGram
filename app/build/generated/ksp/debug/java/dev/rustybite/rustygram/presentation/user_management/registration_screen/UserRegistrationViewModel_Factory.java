package dev.rustybite.rustygram.presentation.user_management.registration_screen;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.local.SessionManager;
import dev.rustybite.rustygram.data.repository.UserRegistrationRepository;
import dev.rustybite.rustygram.util.ResourceProvider;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class UserRegistrationViewModel_Factory implements Factory<UserRegistrationViewModel> {
  private final Provider<UserRegistrationRepository> registrationRepositoryProvider;

  private final Provider<ResourceProvider> resourcesProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  public UserRegistrationViewModel_Factory(
      Provider<UserRegistrationRepository> registrationRepositoryProvider,
      Provider<ResourceProvider> resourcesProvider,
      Provider<SessionManager> sessionManagerProvider) {
    this.registrationRepositoryProvider = registrationRepositoryProvider;
    this.resourcesProvider = resourcesProvider;
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public UserRegistrationViewModel get() {
    return newInstance(registrationRepositoryProvider.get(), resourcesProvider.get(), sessionManagerProvider.get());
  }

  public static UserRegistrationViewModel_Factory create(
      Provider<UserRegistrationRepository> registrationRepositoryProvider,
      Provider<ResourceProvider> resourcesProvider,
      Provider<SessionManager> sessionManagerProvider) {
    return new UserRegistrationViewModel_Factory(registrationRepositoryProvider, resourcesProvider, sessionManagerProvider);
  }

  public static UserRegistrationViewModel newInstance(
      UserRegistrationRepository registrationRepository, ResourceProvider resources,
      SessionManager sessionManager) {
    return new UserRegistrationViewModel(registrationRepository, resources, sessionManager);
  }
}
