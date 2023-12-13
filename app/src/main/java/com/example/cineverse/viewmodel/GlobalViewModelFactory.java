package com.example.cineverse.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GlobalViewModelFactory<T extends ViewModel>
        implements ViewModelProvider.Factory {

    private final T viewModelInstance;

    public GlobalViewModelFactory(T viewModelInstance) {
        this.viewModelInstance = viewModelInstance;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <Y extends ViewModel> Y create(@NonNull Class<Y> modelClass) {
        return (Y) viewModelInstance;
    }

}
