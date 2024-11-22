package dev.rustybite.rustygram.domain.local;

import android.content.ContentResolver;
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
public final class MediaDataSourceImpl_Factory implements Factory<MediaDataSourceImpl> {
  private final Provider<ContentResolver> contentResolverProvider;

  public MediaDataSourceImpl_Factory(Provider<ContentResolver> contentResolverProvider) {
    this.contentResolverProvider = contentResolverProvider;
  }

  @Override
  public MediaDataSourceImpl get() {
    return newInstance(contentResolverProvider.get());
  }

  public static MediaDataSourceImpl_Factory create(
      Provider<ContentResolver> contentResolverProvider) {
    return new MediaDataSourceImpl_Factory(contentResolverProvider);
  }

  public static MediaDataSourceImpl newInstance(ContentResolver contentResolver) {
    return new MediaDataSourceImpl(contentResolver);
  }
}
