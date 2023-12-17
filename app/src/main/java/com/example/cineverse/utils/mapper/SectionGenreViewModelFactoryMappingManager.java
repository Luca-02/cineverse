package com.example.cineverse.utils.mapper;

import androidx.lifecycle.ViewModelProvider;

import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.MovieFromGenreViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.section.TvFromGenreViewModel;

import java.util.HashMap;
import java.util.Map;

public class SectionGenreViewModelFactoryMappingManager {

    private static final Map<Class<? extends AbstractSectionContentViewModel>,
            Class<? extends ViewModelProvider.Factory>> factoryMap = new HashMap<>();

    static {
        factoryMap.put(TvFromGenreViewModel.class, TvFromGenreViewModel.Factory.class);
        factoryMap.put(MovieFromGenreViewModel.class, MovieFromGenreViewModel.Factory.class);
    }

    public static Class<? extends ViewModelProvider.Factory> getFactoryClass(
            Class<? extends AbstractSectionContentViewModel> viewModelClass) {
        return factoryMap.get(viewModelClass);
    }

}
