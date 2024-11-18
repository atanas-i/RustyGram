package dev.rustybite.rustygram.presentation;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import dev.rustybite.rustygram.data.local.SessionManager;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class RustyGramActivity_MembersInjector implements MembersInjector<RustyGramActivity> {
  private final Provider<SessionManager> sessionManagerProvider;

  public RustyGramActivity_MembersInjector(Provider<SessionManager> sessionManagerProvider) {
    this.sessionManagerProvider = sessionManagerProvider;
  }

  public static MembersInjector<RustyGramActivity> create(
      Provider<SessionManager> sessionManagerProvider) {
    return new RustyGramActivity_MembersInjector(sessionManagerProvider);
  }

  @Override
  public void injectMembers(RustyGramActivity instance) {
    injectSessionManager(instance, sessionManagerProvider.get());
  }

  @InjectedFieldSignature("dev.rustybite.rustygram.presentation.RustyGramActivity.sessionManager")
  public static void injectSessionManager(RustyGramActivity instance,
      SessionManager sessionManager) {
    instance.sessionManager = sessionManager;
  }
}
