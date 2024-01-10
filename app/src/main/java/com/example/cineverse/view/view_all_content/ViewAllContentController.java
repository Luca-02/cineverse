package com.example.cineverse.view.view_all_content;

import com.example.cineverse.view.verified_account.fragment.home.HomeFragment;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModelFactory;

/**
 * The {@link ViewAllContentController} class serves as a singleton controller designed to facilitate
 * seamless communication between the {@link HomeFragment} and {@link ViewAllContentActivity}. Its primary purpose
 * is to convey crucial information about the content section to be presented in the {@link ViewAllContentActivity}.
 * The class employs the Singleton pattern to ensure a solitary instance persists throughout the application's lifecycle.
 *
 * <p>
 * Usage:
 * The typical usage involves obtaining the singleton instance through {@link #getInstance()} and subsequently setting
 * the necessary parameters using {@link #setParameters(AbstractSectionContentViewModelFactory, Class)}. This allows
 * other components, such as the {@link HomeFragment}, to communicate vital details about the content section that needs
 * to be displayed in the {@link ViewAllContentActivity}. After the operation is complete, it is advisable to call
 * {@link #clearParameters()} to reset the stored parameters.
 * </p>
 *
 * <p>
 * Example:
 * <pre>
 * {@code
 * ViewAllContentController controller = ViewAllContentController.getInstance();
 * controller.setParameters(viewModelFactory, viewModelClass);
 * // Further interactions or communications
 * controller.clearParameters();
 * }
 * </pre>
 * </p>
 */
public class ViewAllContentController {

    private static ViewAllContentController instance = null;
    private AbstractSectionContentViewModelFactory
            <? extends AbstractSectionContentViewModel> viewModelFactory;
    private Class<? extends AbstractSectionContentViewModel> viewModelClass;

    private ViewAllContentController() {}

    /**
     * Retrieves the singleton instance of the {@link ViewAllContentController}.
     *
     * @return The singleton instance of the {@link ViewAllContentController}.
     */
    public static synchronized ViewAllContentController getInstance() {
        if (instance == null) {
            instance = new ViewAllContentController();
        }
        return instance;
    }

    /**
     * Gets the ViewModel factory associated with the content section.
     *
     * @return The ViewModel factory for the content section.
     */
    public synchronized AbstractSectionContentViewModelFactory
            <? extends AbstractSectionContentViewModel> getViewModelFactory() {
        return viewModelFactory;
    }

    /**
     * Gets the ViewModel class associated with the content section.
     *
     * @return The ViewModel class for the content section.
     */
    public synchronized Class<? extends AbstractSectionContentViewModel> getViewModelClass() {
        return viewModelClass;
    }

    /**
     * Sets the parameters for the content section, including the ViewModel factory and class.
     *
     * @param viewModelFactory The ViewModel factory for the content section.
     * @param viewModelClass   The ViewModel class for the content section.
     */
    public synchronized void setParameters(
            AbstractSectionContentViewModelFactory
                    <? extends AbstractSectionContentViewModel> viewModelFactory,
            Class<? extends AbstractSectionContentViewModel> viewModelClass) {
        clearParameters();
        this.viewModelFactory = viewModelFactory;
        this.viewModelClass = viewModelClass;
    }

    /**
     * Clears the parameters stored in the controller, resetting them to null.
     */
    public synchronized void clearParameters() {
        viewModelFactory = null;
        viewModelClass = null;
    }

}
