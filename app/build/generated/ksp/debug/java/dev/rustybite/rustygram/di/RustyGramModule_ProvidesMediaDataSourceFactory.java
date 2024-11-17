package dev.rustybite.rustygram.di;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.local.MediaDataSource;
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
public final class RustyGramModule_ProvidesMediaDataSourceFactory implements Factory<MediaDataSource> {
  private final Provider<Context> contextProvider;

  public RustyGramModule_ProvidesMediaDataSourceFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public MediaDataSource get() {
    return providesMediaDataSource(contextProvider.get());
  }

  public static RustyGramModule_ProvidesMediaDataSourceFactory create(
      Provider<Context> contextProvider) {
    return new RustyGramModule_ProvidesMediaDataSourceFactory(contextProvider);
  }

  public static MediaDataSource providesMediaDataSource(Context context) {
    return Preconditions.checkNotNullFromProvides(RustyGramModule.INSTANCE.providesMediaDataSource(context));
  }
}
