package dev.rustybite.rustygram.presentation.posts.create_post;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.local.SessionManager;
import dev.rustybite.rustygram.data.repository.GalleryRepository;
import dev.rustybite.rustygram.data.repository.PostsRepository;
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
public final class CreatePostViewModel_Factory implements Factory<CreatePostViewModel> {
  private final Provider<PostsRepository> repositoryProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  private final Provider<TokenManagementRepository> tokenRepositoryProvider;

  private final Provider<ResourceProvider> resProvider;

  private final Provider<GalleryRepository> galleryRepositoryProvider;

  private final Provider<StorageRepository> storageRepositoryProvider;

  public CreatePostViewModel_Factory(Provider<PostsRepository> repositoryProvider,
      Provider<SessionManager> sessionManagerProvider,
      Provider<TokenManagementRepository> tokenRepositoryProvider,
      Provider<ResourceProvider> resProvider, Provider<GalleryRepository> galleryRepositoryProvider,
      Provider<StorageRepository> storageRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.sessionManagerProvider = sessionManagerProvider;
    this.tokenRepositoryProvider = tokenRepositoryProvider;
    this.resProvider = resProvider;
    this.galleryRepositoryProvider = galleryRepositoryProvider;
    this.storageRepositoryProvider = storageRepositoryProvider;
  }

  @Override
  public CreatePostViewModel get() {
    return newInstance(repositoryProvider.get(), sessionManagerProvider.get(), tokenRepositoryProvider.get(), resProvider.get(), galleryRepositoryProvider.get(), storageRepositoryProvider.get());
  }

  public static CreatePostViewModel_Factory create(Provider<PostsRepository> repositoryProvider,
      Provider<SessionManager> sessionManagerProvider,
      Provider<TokenManagementRepository> tokenRepositoryProvider,
      Provider<ResourceProvider> resProvider, Provider<GalleryRepository> galleryRepositoryProvider,
      Provider<StorageRepository> storageRepositoryProvider) {
    return new CreatePostViewModel_Factory(repositoryProvider, sessionManagerProvider, tokenRepositoryProvider, resProvider, galleryRepositoryProvider, storageRepositoryProvider);
  }

  public static CreatePostViewModel newInstance(PostsRepository repository,
      SessionManager sessionManager, TokenManagementRepository tokenRepository,
      ResourceProvider resProvider, GalleryRepository galleryRepository,
      StorageRepository storageRepository) {
    return new CreatePostViewModel(repository, sessionManager, tokenRepository, resProvider, galleryRepository, storageRepository);
  }
}
