package com.example.cineverse.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * The {@link GlobalViewModelFactory} abstract class is a custom ViewModelFactory that provides a single
 * instance of a specified ViewModel.
 *
 * @param <T> The type of {@link ViewModel} for which the factory provides instances.
 */
public abstract class GlobalViewModelFactory<T extends ViewModel>
        implements ViewModelProvider.Factory {

    private final T viewModelInstance;

    /**
     * Constructs a {@link GlobalViewModelFactory} with the specified ViewModel instance.
     *
     * @param viewModelInstance The instance of the {@link ViewModel} to be provided by the factory.
     */
    public GlobalViewModelFactory(T viewModelInstance) {
        this.viewModelInstance = viewModelInstance;
    }

    /**
     * Creates and returns a ViewModel of the specified type.
     *
     * @param modelClass The class of the {@link ViewModel} to create.
     * @param <Y>        The type of {@link ViewModel} to create.
     * @return A new instance of the specified {@link ViewModel}.
     */
    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <Y extends ViewModel> Y create(@NonNull Class<Y> modelClass) {
        return (Y) viewModelInstance;
    }

}
