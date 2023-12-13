package com.example.cineverse.view.view_all_content;

import android.app.Application;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.R;
import com.example.cineverse.data.model.genre.Genre;
import com.example.cineverse.databinding.ActivityViewAllContentBinding;
import com.example.cineverse.exception.ViewModelCreationException;
import com.example.cineverse.handler.callback.BackPressedHandler;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.genre.AbstractSectionGenreViewModel;

import java.lang.reflect.InvocationTargetException;

public class ViewAllContentActivity extends AppCompatActivity {

    public static final String TITLE_STRING_ID_TAG = "TitleStringId";
    public static final String VIEW_MODEL_CLASS_NAME_TAG = "ViewModelClassName";
    public static final String GENRE_TAG = "Genre";

    private ActivityViewAllContentBinding binding;
    private NavController navController;

    private int titleStringId;
    private String viewModelClassName;
    private Genre genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getExtras();
        setActionBar();
        setNavController();
        BackPressedHandler.handleOnBackPressedCallback(this, navController);
        getWindow().setNavigationBarColor(android.R.attr.colorBackground);
        binding.materialToolbar.setNavigationOnClickListener(
                view -> getOnBackPressedDispatcher().onBackPressed());
    }

    public void getExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            titleStringId = bundle.getInt(TITLE_STRING_ID_TAG);
            viewModelClassName = bundle.getString(VIEW_MODEL_CLASS_NAME_TAG);
            genre = bundle.getParcelable(GENRE_TAG);
        }
    }

    /**
     * Sets up the {@link ActionBar} with the provided Toolbar.
     */
    private void setActionBar() {
        setSupportActionBar(binding.materialToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (genre != null) {
                actionBar.setTitle(genre.getName());
            } else {
                actionBar.setTitle(titleStringId);
            }
        }
    }

    /**
     * Sets up the {@link NavController} for navigating between destinations.
     * This method finds the {@link NavHostFragment} and initializes the {@link NavController}.
     */
    private void setNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
    }

    public AbstractSectionContentViewModel getViewModel(ViewModelStoreOwner owner) {
        try {
            if (viewModelClassName != null) {
                Class<?> loadedClass = Class.forName(viewModelClassName);

                if (AbstractSectionContentViewModel.class.isAssignableFrom(loadedClass)) {
                    return createContentViewModel(loadedClass, owner);
                } else if (AbstractSectionGenreViewModel.class.isAssignableFrom(loadedClass) && genre != null) {
                    return createGenreViewModel(loadedClass, owner);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            throw new ViewModelCreationException(e);
        }
        return null;
    }

    private AbstractSectionContentViewModel createContentViewModel(Class<?> loadedClass, ViewModelStoreOwner owner) {
        Class<? extends AbstractSectionContentViewModel> contentViewModelClass =
                loadedClass.asSubclass(AbstractSectionContentViewModel.class);
        return new ViewModelProvider(owner).get(contentViewModelClass);
    }

    private AbstractSectionContentViewModel createGenreViewModel(Class<?> loadedClass, ViewModelStoreOwner owner)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<? extends AbstractSectionGenreViewModel> genreViewModelClass =
                loadedClass.asSubclass(AbstractSectionGenreViewModel.class);

        AbstractSectionGenreViewModel genreViewModel = genreViewModelClass
                .getConstructor(Application.class)
                .newInstance(getApplication());

        ViewModelProvider.Factory factory =
                genreViewModel.getSectionContentFactory(genre.getId());

        return new ViewModelProvider(owner, factory).get(
                genreViewModel.getSectionContentViewModelClass());
    }

}