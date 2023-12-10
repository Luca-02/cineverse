package com.example.cineverse.view.view_all_content;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.R;
import com.example.cineverse.databinding.ActivityViewAllContentBinding;
import com.example.cineverse.handler.callback.BackPressedHandler;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.AbstractSectionViewModel;

public class ViewAllContentActivity extends AppCompatActivity {

    public static final String TITLE_STRING_ID_TAG = "TitleStringId";
    public static final String VIEW_MODEL_CLASS_NAME_TAG = "ViewModelClassName";

    private ActivityViewAllContentBinding binding;
    private NavController navController;

    private int titleStringId;
    private String viewModelClassName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getExtras();
        setActionBar();
        setNavController();
        BackPressedHandler.handleOnBackPressedCallback(this, navController);
        binding.materialToolbar.setNavigationOnClickListener(
                view -> getOnBackPressedDispatcher().onBackPressed());
    }

    public void getExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            titleStringId = bundle.getInt(TITLE_STRING_ID_TAG);
            viewModelClassName = bundle.getString(VIEW_MODEL_CLASS_NAME_TAG);
        }
    }

    /**
     * Sets up the {@link ActionBar} with the provided Toolbar.
     */
    private void setActionBar() {
        setSupportActionBar(binding.materialToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(titleStringId);
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

    public Class<? extends AbstractSectionViewModel> getViewModelClass() {
        try {
            if (viewModelClassName != null) {
                Class<?> loadedClass = Class.forName(viewModelClassName);
                return loadedClass.asSubclass(AbstractSectionViewModel.class);
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}