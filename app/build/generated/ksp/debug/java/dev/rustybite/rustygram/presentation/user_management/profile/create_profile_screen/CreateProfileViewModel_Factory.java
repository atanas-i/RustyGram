package dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.local.SessionManager;
import dev.rustybite.rustygram.data.repository.ProfileRepository;
import dev.rustybite.rustygram.data.repository.StorageRepository;
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
public final class CreateProfileViewModel_Factory implements Factory<CreateProfileViewModel> {
  private final Provider<ProfileRepository> repositoryProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  private final Provider<ResourceProvider> resProvider;

  private final Provider<StorageRepository> storageRepositoryProvider;

  private final Provider<TokenManagementRepository> tokenRepositoryProvider;

  public CreateProfileViewModel_Factory(Provider<ProfileRepository> repositoryProvider,
      Provider<SessionManager> sessionManagerProvider, Provider<ResourceProvider> resProvider,
      Provider<StorageRepository> storageRepositoryProvider,
      Provider<TokenManagementRepository> tokenRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.sessionManagerProvider = sessionManagerProvider;
    this.resProvider = resProvider;
    this.storageRepositoryProvider = storageRepositoryProvider;
    this.tokenRepositoryProvider = tokenRepositoryProvider;
  }

  @Override
  public CreateProfileViewModel get() {
    return newInstance(repositoryProvider.get(), sessionManagerProvider.get(), resProvider.get(), storageRepositoryProvider.get(), tokenRepositoryProvider.get());
  }

  public static CreateProfileViewModel_Factory create(
      Provider<ProfileRepository> repositoryProvider,
      Provider<SessionManager> sessionManagerProvider, Provider<ResourceProvider> resProvider,
      Provider<StorageRepository> storageRepositoryProvider,
      Provider<TokenManagementRepository> tokenRepositoryProvider) {
    return new CreateProfileViewModel_Factory(repositoryProvider, sessionManagerProvider, resProvider, storageRepositoryProvider, tokenRepositoryProvider);
  }

  public static CreateProfileViewModel newInstance(ProfileRepository repository,
      SessionManager sessionManager, ResourceProvider resProvider,
      StorageRepository storageRepository, TokenManagementRepository tokenRepository) {
    return new CreateProfileViewModel(repository, sessionManager, resProvider, storageRepository, tokenRepository);
  }
}
