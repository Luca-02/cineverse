package com.example.cineverse.view.view_all_content;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cineverse.data.model.content.poster.AbstractPoster;
import com.example.cineverse.databinding.ActivityViewAllContentBinding;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.AbstractSectionViewModel;

public class ViewAllContentActivity extends AppCompatActivity {

    public static final String TITLE_STRING_ID_TAG = "TitleStringId";
    public static final String VIEW_MODEL_CLASS_NAME_TAG = "ViewModelClassName";

    private int titleStringId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityViewAllContentBinding binding = ActivityViewAllContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            titleStringId = bundle.getInt(TITLE_STRING_ID_TAG);
            String viewModelClassName = bundle.getString(VIEW_MODEL_CLASS_NAME_TAG);
            Log.d("MY_TAG", "" + viewModelClassName);

            try {
                Class<? extends AbstractSectionViewModel<? extends AbstractPoster>> viewModelClass =
                        (Class<? extends AbstractSectionViewModel<? extends AbstractPoster>>) Class.forName(viewModelClassName);

                Log.d("MY_TAG", "" + viewModelClass);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        setSupportActionBar(binding.materialToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(titleStringId);
        }
    }

}