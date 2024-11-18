package dev.rustybite.rustygram.domain.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.local.MediaDataSource;
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
public final class GalleryRepositoryImpl_Factory implements Factory<GalleryRepositoryImpl> {
  private final Provider<MediaDataSource> mediaDataSourceProvider;

  public GalleryRepositoryImpl_Factory(Provider<MediaDataSource> mediaDataSourceProvider) {
    this.mediaDataSourceProvider = mediaDataSourceProvider;
  }

  @Override
  public GalleryRepositoryImpl get() {
    return newInstance(mediaDataSourceProvider.get());
  }

  public static GalleryRepositoryImpl_Factory create(
      Provider<MediaDataSource> mediaDataSourceProvider) {
    return new GalleryRepositoryImpl_Factory(mediaDataSourceProvider);
  }

  public static GalleryRepositoryImpl newInstance(MediaDataSource mediaDataSource) {
    return new GalleryRepositoryImpl(mediaDataSource);
  }
}
