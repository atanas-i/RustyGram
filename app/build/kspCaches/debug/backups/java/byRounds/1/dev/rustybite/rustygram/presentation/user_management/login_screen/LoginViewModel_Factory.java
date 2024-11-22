package dev.rustybite.rustygram.presentation.user_management.login_screen;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.local.SessionManager;
import dev.rustybite.rustygram.data.repository.LoginRepository;
import dev.rustybite.rustygram.data.repository.TokenManagementRepository;
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
public final class LoginViewModel_Factory implements Factory<LoginViewModel> {
  private final Provider<LoginRepository> loginRepositoryProvider;

  private final Provider<ResourceProvider> resourcesProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  private final Provider<TokenManagementRepository> tokenManagementRepositoryProvider;

  public LoginViewModel_Factory(Provider<LoginRepository> loginRepositoryProvider,
      Provider<ResourceProvider> resourcesProvider, Provider<SessionManager> sessionManagerProvider,
      Provider<TokenManagementRepository> tokenManagementRepositoryProvider) {
    this.loginRepositoryProvider = loginRepositoryProvider;
    this.resourcesProvider = resourcesProvider;
    this.sessionManagerProvider = sessionManagerProvider;
    this.tokenManagementRepositoryProvider = tokenManagementRepositoryProvider;
  }

  @Override
  public LoginViewModel get() {
    return newInstance(loginRepositoryProvider.get(), resourcesProvider.get(), sessionManagerProvider.get(), tokenManagementRepositoryProvider.get());
  }

  public static LoginViewModel_Factory create(Provider<LoginRepository> loginRepositoryProvider,
      Provider<ResourceProvider> resourcesProvider, Provider<SessionManager> sessionManagerProvider,
      Provider<TokenManagementRepository> tokenManagementRepositoryProvider) {
    return new LoginViewModel_Factory(loginRepositoryProvider, resourcesProvider, sessionManagerProvider, tokenManagementRepositoryProvider);
  }

  public static LoginViewModel newInstance(LoginRepository loginRepository,
      ResourceProvider resources, SessionManager sessionManager,
      TokenManagementRepository tokenManagementRepository) {
    return new LoginViewModel(loginRepository, resources, sessionManager, tokenManagementRepository);
  }
}
