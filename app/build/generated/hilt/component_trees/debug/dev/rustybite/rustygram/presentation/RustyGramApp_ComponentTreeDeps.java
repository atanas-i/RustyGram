package dev.rustybite.rustygram.presentation;

import dagger.hilt.internal.aggregatedroot.codegen._dev_rustybite_rustygram_presentation_RustyGramApp;
import dagger.hilt.internal.componenttreedeps.ComponentTreeDeps;
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_components_ActivityComponent;
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_components_ActivityRetainedComponent;
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_components_FragmentComponent;
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_components_ServiceComponent;
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_components_ViewComponent;
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_components_ViewModelComponent;
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_components_ViewWithFragmentComponent;
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_internal_builders_ActivityComponentBuilder;
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_internal_builders_ActivityRetainedComponentBuilder;
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_internal_builders_FragmentComponentBuilder;
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_internal_builders_ServiceComponentBuilder;
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_internal_builders_ViewComponentBuilder;
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_internal_builders_ViewModelComponentBuilder;
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_internal_builders_ViewWithFragmentComponentBuilder;
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_components_SingletonComponent;
import hilt_aggregated_deps._dagger_hilt_android_flags_FragmentGetContextFix_FragmentGetContextFixEntryPoint;
import hilt_aggregated_deps._dagger_hilt_android_flags_HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule;
import hilt_aggregated_deps._dagger_hilt_android_internal_lifecycle_DefaultViewModelFactories_ActivityEntryPoint;
import hilt_aggregated_deps._dagger_hilt_android_internal_lifecycle_DefaultViewModelFactories_FragmentEntryPoint;
import hilt_aggregated_deps._dagger_hilt_android_internal_lifecycle_HiltViewModelFactory_ViewModelFactoriesEntryPoint;
import hilt_aggregated_deps._dagger_hilt_android_internal_lifecycle_HiltWrapper_DefaultViewModelFactories_ActivityModule;
import hilt_aggregated_deps._dagger_hilt_android_internal_lifecycle_HiltWrapper_HiltViewModelFactory_ActivityCreatorEntryPoint;
import hilt_aggregated_deps._dagger_hilt_android_internal_lifecycle_HiltWrapper_HiltViewModelFactory_ViewModelModule;
import hilt_aggregated_deps._dagger_hilt_android_internal_managers_ActivityComponentManager_ActivityComponentBuilderEntryPoint;
import hilt_aggregated_deps._dagger_hilt_android_internal_managers_FragmentComponentManager_FragmentComponentBuilderEntryPoint;
import hilt_aggregated_deps._dagger_hilt_android_internal_managers_HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedComponentBuilderEntryPoint;
import hilt_aggregated_deps._dagger_hilt_android_internal_managers_HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedLifecycleEntryPoint;
import hilt_aggregated_deps._dagger_hilt_android_internal_managers_HiltWrapper_ActivityRetainedComponentManager_LifecycleModule;
import hilt_aggregated_deps._dagger_hilt_android_internal_managers_HiltWrapper_SavedStateHandleModule;
import hilt_aggregated_deps._dagger_hilt_android_internal_managers_ServiceComponentManager_ServiceComponentBuilderEntryPoint;
import hilt_aggregated_deps._dagger_hilt_android_internal_managers_ViewComponentManager_ViewComponentBuilderEntryPoint;
import hilt_aggregated_deps._dagger_hilt_android_internal_managers_ViewComponentManager_ViewWithFragmentComponentBuilderEntryPoint;
import hilt_aggregated_deps._dagger_hilt_android_internal_modules_ApplicationContextModule;
import hilt_aggregated_deps._dagger_hilt_android_internal_modules_HiltWrapper_ActivityModule;
import hilt_aggregated_deps._dev_rustybite_rustygram_di_RustyGramModule;
import hilt_aggregated_deps._dev_rustybite_rustygram_presentation_RustyGramActivity_GeneratedInjector;
import hilt_aggregated_deps._dev_rustybite_rustygram_presentation_RustyGramApp_GeneratedInjector;
import hilt_aggregated_deps._dev_rustybite_rustygram_presentation_RustyGramViewModel_HiltModules_BindsModule;
import hilt_aggregated_deps._dev_rustybite_rustygram_presentation_RustyGramViewModel_HiltModules_KeyModule;
import hilt_aggregated_deps._dev_rustybite_rustygram_presentation_posts_create_post_CreatePostViewModel_HiltModules_BindsModule;
import hilt_aggregated_deps._dev_rustybite_rustygram_presentation_posts_create_post_CreatePostViewModel_HiltModules_KeyModule;
import hilt_aggregated_deps._dev_rustybite_rustygram_presentation_posts_fetch_posts_GetPostsViewModel_HiltModules_BindsModule;
import hilt_aggregated_deps._dev_rustybite_rustygram_presentation_posts_fetch_posts_GetPostsViewModel_HiltModules_KeyModule;
import hilt_aggregated_deps._dev_rustybite_rustygram_presentation_user_management_login_screen_LoginViewModel_HiltModules_BindsModule;
import hilt_aggregated_deps._dev_rustybite_rustygram_presentation_user_management_login_screen_LoginViewModel_HiltModules_KeyModule;
import hilt_aggregated_deps._dev_rustybite_rustygram_presentation_user_management_profile_create_profile_screen_CreateProfileViewModel_HiltModules_BindsModule;
import hilt_aggregated_deps._dev_rustybite_rustygram_presentation_user_management_profile_create_profile_screen_CreateProfileViewModel_HiltModules_KeyModule;
import hilt_aggregated_deps._dev_rustybite_rustygram_presentation_user_management_profile_view_profile_screen_ProfileViewModel_HiltModules_BindsModule;
import hilt_aggregated_deps._dev_rustybite_rustygram_presentation_user_management_profile_view_profile_screen_ProfileViewModel_HiltModules_KeyModule;
import hilt_aggregated_deps._dev_rustybite_rustygram_presentation_user_management_registration_screen_UserRegistrationViewModel_HiltModules_BindsModule;
import hilt_aggregated_deps._dev_rustybite_rustygram_presentation_user_management_registration_screen_UserRegistrationViewModel_HiltModules_KeyModule;

@ComponentTreeDeps(
    rootDeps = _dev_rustybite_rustygram_presentation_RustyGramApp.class,
    defineComponentDeps = {
        _dagger_hilt_android_components_ActivityComponent.class,
        _dagger_hilt_android_components_ActivityRetainedComponent.class,
        _dagger_hilt_android_components_FragmentComponent.class,
        _dagger_hilt_android_components_ServiceComponent.class,
        _dagger_hilt_android_components_ViewComponent.class,
        _dagger_hilt_android_components_ViewModelComponent.class,
        _dagger_hilt_android_components_ViewWithFragmentComponent.class,
        _dagger_hilt_android_internal_builders_ActivityComponentBuilder.class,
        _dagger_hilt_android_internal_builders_ActivityRetainedComponentBuilder.class,
        _dagger_hilt_android_internal_builders_FragmentComponentBuilder.class,
        _dagger_hilt_android_internal_builders_ServiceComponentBuilder.class,
        _dagger_hilt_android_internal_builders_ViewComponentBuilder.class,
        _dagger_hilt_android_internal_builders_ViewModelComponentBuilder.class,
        _dagger_hilt_android_internal_builders_ViewWithFragmentComponentBuilder.class,
        _dagger_hilt_components_SingletonComponent.class
    },
    aggregatedDeps = {
        _dagger_hilt_android_flags_FragmentGetContextFix_FragmentGetContextFixEntryPoint.class,
        _dagger_hilt_android_flags_HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule.class,
        _dagger_hilt_android_internal_lifecycle_DefaultViewModelFactories_ActivityEntryPoint.class,
        _dagger_hilt_android_internal_lifecycle_DefaultViewModelFactories_FragmentEntryPoint.class,
        _dagger_hilt_android_internal_lifecycle_HiltViewModelFactory_ViewModelFactoriesEntryPoint.class,
        _dagger_hilt_android_internal_lifecycle_HiltWrapper_DefaultViewModelFactories_ActivityModule.class,
        _dagger_hilt_android_internal_lifecycle_HiltWrapper_HiltViewModelFactory_ActivityCreatorEntryPoint.class,
        _dagger_hilt_android_internal_lifecycle_HiltWrapper_HiltViewModelFactory_ViewModelModule.class,
        _dagger_hilt_android_internal_managers_ActivityComponentManager_ActivityComponentBuilderEntryPoint.class,
        _dagger_hilt_android_internal_managers_FragmentComponentManager_FragmentComponentBuilderEntryPoint.class,
        _dagger_hilt_android_internal_managers_HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedComponentBuilderEntryPoint.class,
        _dagger_hilt_android_internal_managers_HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedLifecycleEntryPoint.class,
        _dagger_hilt_android_internal_managers_HiltWrapper_ActivityRetainedComponentManager_LifecycleModule.class,
        _dagger_hilt_android_internal_managers_HiltWrapper_SavedStateHandleModule.class,
        _dagger_hilt_android_internal_managers_ServiceComponentManager_ServiceComponentBuilderEntryPoint.class,
        _dagger_hilt_android_internal_managers_ViewComponentManager_ViewComponentBuilderEntryPoint.class,
        _dagger_hilt_android_internal_managers_ViewComponentManager_ViewWithFragmentComponentBuilderEntryPoint.class,
        _dagger_hilt_android_internal_modules_ApplicationContextModule.class,
        _dagger_hilt_android_internal_modules_HiltWrapper_ActivityModule.class,
        _dev_rustybite_rustygram_di_RustyGramModule.class,
        _dev_rustybite_rustygram_presentation_RustyGramActivity_GeneratedInjector.class,
        _dev_rustybite_rustygram_presentation_RustyGramApp_GeneratedInjector.class,
        _dev_rustybite_rustygram_presentation_RustyGramViewModel_HiltModules_BindsModule.class,
        _dev_rustybite_rustygram_presentation_RustyGramViewModel_HiltModules_KeyModule.class,
        _dev_rustybite_rustygram_presentation_posts_create_post_CreatePostViewModel_HiltModules_BindsModule.class,
        _dev_rustybite_rustygram_presentation_posts_create_post_CreatePostViewModel_HiltModules_KeyModule.class,
        _dev_rustybite_rustygram_presentation_posts_fetch_posts_GetPostsViewModel_HiltModules_BindsModule.class,
        _dev_rustybite_rustygram_presentation_posts_fetch_posts_GetPostsViewModel_HiltModules_KeyModule.class,
        _dev_rustybite_rustygram_presentation_user_management_login_screen_LoginViewModel_HiltModules_BindsModule.class,
        _dev_rustybite_rustygram_presentation_user_management_login_screen_LoginViewModel_HiltModules_KeyModule.class,
        _dev_rustybite_rustygram_presentation_user_management_profile_create_profile_screen_CreateProfileViewModel_HiltModules_BindsModule.class,
        _dev_rustybite_rustygram_presentation_user_management_profile_create_profile_screen_CreateProfileViewModel_HiltModules_KeyModule.class,
        _dev_rustybite_rustygram_presentation_user_management_profile_view_profile_screen_ProfileViewModel_HiltModules_BindsModule.class,
        _dev_rustybite_rustygram_presentation_user_management_profile_view_profile_screen_ProfileViewModel_HiltModules_KeyModule.class,
        _dev_rustybite_rustygram_presentation_user_management_registration_screen_UserRegistrationViewModel_HiltModules_BindsModule.class,
        _dev_rustybite_rustygram_presentation_user_management_registration_screen_UserRegistrationViewModel_HiltModules_KeyModule.class
    }
)
public final class RustyGramApp_ComponentTreeDeps {
}
