package dev.rustybite.rustygram.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.rustybite.rustygram.data.local.MediaDataSource;
import dev.rustybite.rustygram.data.repository.GalleryRepository;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class RustyGramModule_ProvidesGalleryRepositoryFactory implements Factory<GalleryRepository> {
  private final Provider<MediaDataSource> mediaDataSourceProvider;

  public RustyGramModule_ProvidesGalleryRepositoryFactory(
      Provider<MediaDataSource> mediaDataSourceProvider) {
    this.mediaDataSourceProvider = mediaDataSourceProvider;
  }

  @Override
  public GalleryRepository get() {
    return providesGalleryRepository(mediaDataSourceProvider.get());
  }

  public static RustyGramModule_ProvidesGalleryRepositoryFactory create(
      Provider<MediaDataSource> mediaDataSourceProvider) {
    return new RustyGramModule_ProvidesGalleryRepositoryFactory(mediaDataSourceProvider);
  }

  public static GalleryRepository providesGalleryRepository(MediaDataSource mediaDataSource) {
    return Preconditions.checkNotNullFromProvides(RustyGramModule.INSTANCE.providesGalleryRepository(mediaDataSource));
  }
}
