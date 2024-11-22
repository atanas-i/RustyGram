package dev.rustybite.rustygram.presentation.user_management.profile.view_profile_screen;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.local.SessionManager;
import dev.rustybite.rustygram.data.repository.ProfileRepository;
import dev.rustybite.rustygram.data.repository.TokenManagementRepository;
import dev.rustybite.rustygram.data.repository.UserRepository;
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
public final class ProfileViewModel_Factory implements Factory<ProfileViewModel> {
  private final Provider<ProfileRepository> repositoryProvider;

  private final Provider<TokenManagementRepository> tokenRepositoryProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<ResourceProvider> resProvider;

  public ProfileViewModel_Factory(Provider<ProfileRepository> repositoryProvider,
      Provider<TokenManagementRepository> tokenRepositoryProvider,
      Provider<SessionManager> sessionManagerProvider,
      Provider<UserRepository> userRepositoryProvider, Provider<ResourceProvider> resProvider) {
    this.repositoryProvider = repositoryProvider;
    this.tokenRepositoryProvider = tokenRepositoryProvider;
    this.sessionManagerProvider = sessionManagerProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.resProvider = resProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(repositoryProvider.get(), tokenRepositoryProvider.get(), sessionManagerProvider.get(), userRepositoryProvider.get(), resProvider.get());
  }

  public static ProfileViewModel_Factory create(Provider<ProfileRepository> repositoryProvider,
      Provider<TokenManagementRepository> tokenRepositoryProvider,
      Provider<SessionManager> sessionManagerProvider,
      Provider<UserRepository> userRepositoryProvider, Provider<ResourceProvider> resProvider) {
    return new ProfileViewModel_Factory(repositoryProvider, tokenRepositoryProvider, sessionManagerProvider, userRepositoryProvider, resProvider);
  }

  public static ProfileViewModel newInstance(ProfileRepository repository,
      TokenManagementRepository tokenRepository, SessionManager sessionManager,
      UserRepository userRepository, ResourceProvider resProvider) {
    return new ProfileViewModel(repository, tokenRepository, sessionManager, userRepository, resProvider);
  }
}
