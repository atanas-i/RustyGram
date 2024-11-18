package dev.rustybite.rustygram.domain.local;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
public final class SessionManagerImpl_Factory implements Factory<SessionManagerImpl> {
  private final Provider<Context> contextProvider;

  public SessionManagerImpl_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SessionManagerImpl get() {
    return newInstance(contextProvider.get());
  }

  public static SessionManagerImpl_Factory create(Provider<Context> contextProvider) {
    return new SessionManagerImpl_Factory(contextProvider);
  }

  public static SessionManagerImpl newInstance(Context context) {
    return new SessionManagerImpl(context);
  }
}
