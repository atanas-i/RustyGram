package dev.rustybite.rustygram.presentation.posts.fetch_posts;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.local.SessionManager;
import dev.rustybite.rustygram.data.repository.PostsRepository;
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
public final class GetPostsViewModel_Factory implements Factory<GetPostsViewModel> {
  private final Provider<PostsRepository> repositoryProvider;

  private final Provider<TokenManagementRepository> tokenRepositoryProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  private final Provider<ResourceProvider> resProvider;

  public GetPostsViewModel_Factory(Provider<PostsRepository> repositoryProvider,
      Provider<TokenManagementRepository> tokenRepositoryProvider,
      Provider<SessionManager> sessionManagerProvider, Provider<ResourceProvider> resProvider) {
    this.repositoryProvider = repositoryProvider;
    this.tokenRepositoryProvider = tokenRepositoryProvider;
    this.sessionManagerProvider = sessionManagerProvider;
    this.resProvider = resProvider;
  }

  @Override
  public GetPostsViewModel get() {
    return newInstance(repositoryProvider.get(), tokenRepositoryProvider.get(), sessionManagerProvider.get(), resProvider.get());
  }

  public static GetPostsViewModel_Factory create(Provider<PostsRepository> repositoryProvider,
      Provider<TokenManagementRepository> tokenRepositoryProvider,
      Provider<SessionManager> sessionManagerProvider, Provider<ResourceProvider> resProvider) {
    return new GetPostsViewModel_Factory(repositoryProvider, tokenRepositoryProvider, sessionManagerProvider, resProvider);
  }

  public static GetPostsViewModel newInstance(PostsRepository repository,
      TokenManagementRepository tokenRepository, SessionManager sessionManager,
      ResourceProvider resProvider) {
    return new GetPostsViewModel(repository, tokenRepository, sessionManager, resProvider);
  }
}
