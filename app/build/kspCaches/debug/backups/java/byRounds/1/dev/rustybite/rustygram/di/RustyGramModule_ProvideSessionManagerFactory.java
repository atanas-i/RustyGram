package dev.rustybite.rustygram.di;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.local.SessionManager;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class RustyGramModule_ProvideSessionManagerFactory implements Factory<SessionManager> {
  private final Provider<Context> contextProvider;

  public RustyGramModule_ProvideSessionManagerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SessionManager get() {
    return provideSessionManager(contextProvider.get());
  }

  public static RustyGramModule_ProvideSessionManagerFactory create(
      Provider<Context> contextProvider) {
    return new RustyGramModule_ProvideSessionManagerFactory(contextProvider);
  }

  public static SessionManager provideSessionManager(Context context) {
    return Preconditions.checkNotNullFromProvides(RustyGramModule.INSTANCE.provideSessionManager(context));
  }
}
