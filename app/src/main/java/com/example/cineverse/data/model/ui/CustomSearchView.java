package com.example.cineverse.data.model.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.search.SearchView;

public class CustomSearchView extends SearchView {

    public CustomSearchView(@NonNull Context context) {
        super(context);
    }

    public CustomSearchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSearchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_UP) {
            this.hide();
            return true;
        }
        return super.dispatchKeyEventPreIme(event);
    }

}
