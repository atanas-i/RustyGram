package dev.rustybite.rustygram.presentation;

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
public final class RustyGramViewModel_Factory implements Factory<RustyGramViewModel> {
  private final Provider<SessionManager> sessionManagerProvider;

  private final Provider<ProfileRepository> profileRepositoryProvider;

  private final Provider<TokenManagementRepository> tokenRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<ResourceProvider> resProvider;

  public RustyGramViewModel_Factory(Provider<SessionManager> sessionManagerProvider,
      Provider<ProfileRepository> profileRepositoryProvider,
      Provider<TokenManagementRepository> tokenRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider, Provider<ResourceProvider> resProvider) {
    this.sessionManagerProvider = sessionManagerProvider;
    this.profileRepositoryProvider = profileRepositoryProvider;
    this.tokenRepositoryProvider = tokenRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.resProvider = resProvider;
  }

  @Override
  public RustyGramViewModel get() {
    return newInstance(sessionManagerProvider.get(), profileRepositoryProvider.get(), tokenRepositoryProvider.get(), userRepositoryProvider.get(), resProvider.get());
  }

  public static RustyGramViewModel_Factory create(Provider<SessionManager> sessionManagerProvider,
      Provider<ProfileRepository> profileRepositoryProvider,
      Provider<TokenManagementRepository> tokenRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider, Provider<ResourceProvider> resProvider) {
    return new RustyGramViewModel_Factory(sessionManagerProvider, profileRepositoryProvider, tokenRepositoryProvider, userRepositoryProvider, resProvider);
  }

  public static RustyGramViewModel newInstance(SessionManager sessionManager,
      ProfileRepository profileRepository, TokenManagementRepository tokenRepository,
      UserRepository userRepository, ResourceProvider resProvider) {
    return new RustyGramViewModel(sessionManager, profileRepository, tokenRepository, userRepository, resProvider);
  }
}
