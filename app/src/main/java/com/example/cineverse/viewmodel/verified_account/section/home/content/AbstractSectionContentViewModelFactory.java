package com.example.cineverse.viewmodel.verified_account.section.home.content;

import com.example.cineverse.viewmodel.GlobalViewModelFactory;

/**
 * The {@link AbstractSectionContentViewModelFactory} class extends {@link GlobalViewModelFactory}
 * and serves as an abstract base class for factories that create instances of
 * {@link AbstractSectionContentViewModel}.
 *
 * @param <T> The type of {@link AbstractSectionContentViewModel}.
 */
public abstract class AbstractSectionContentViewModelFactory<T extends AbstractSectionContentViewModel>
        extends GlobalViewModelFactory<T> {

    /**
     * Constructs a new instance of {@link AbstractSectionContentViewModelFactory}.
     *
     * @param viewModelInstance The instance of the {@link AbstractSectionContentViewModel}.
     */
    public AbstractSectionContentViewModelFactory(T viewModelInstance) {
        super(viewModelInstance);
    }

    /**
     * Gets the class of the {@link AbstractSectionContentViewModel} associated with this factory.
     *
     * @return The class of the {@link AbstractSectionContentViewModel}.
     */
    public abstract Class<? extends AbstractSectionContentViewModel> getViewModelClass();

    /**
     * Creates a new instance of the {@link AbstractSectionContentViewModelFactory}.
     *
     * @return A new instance of the {@link AbstractSectionContentViewModelFactory}.
     */
    public abstract AbstractSectionContentViewModelFactory<T> newInstance();

}
