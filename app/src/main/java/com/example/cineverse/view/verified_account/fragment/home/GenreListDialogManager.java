package com.example.cineverse.view.verified_account.fragment.home;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Window;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cineverse.R;
import com.example.cineverse.adapter.genre.GenreListAdapter;
import com.example.cineverse.data.model.genre.Genre;
import com.example.cineverse.databinding.GenreListDialogLayoutBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link GenreListDialogManager} class represents a custom dialog for displaying a list of genres.
 * It uses a GenreListAdapter to populate the RecyclerView with genre items.
 */
public class GenreListDialogManager {

    private final Dialog dialog;
    private final GenreListDialogLayoutBinding dialogBinding;
    private GenreListAdapter genreListAdapter;

    /**
     * Constructs a GenreListDialog.
     *
     * @param context The context in which the dialog will be shown.
     * @param genreClickListener Listener for handling genre item clicks.
     */
    public GenreListDialogManager(
            Context context,
            GenreListAdapter.OnGenreClickListener genreClickListener) {
        dialogBinding = GenreListDialogLayoutBinding.inflate(LayoutInflater.from(context));
        dialog = new Dialog(context, R.style.Theme_Translucent_NoTitleBar_Fullscreen_Dialog_Animation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogBinding.getRoot());
        setupUI(context, genreClickListener);
    }

    public Dialog getDialog() {
        return dialog;
    }

    /**
     * Sets up the UI components of the dialog, including the RecyclerView.
     *
     * @param context             The context in which the dialog will be shown.
     * @param genreClickListener Listener for handling genre item clicks.
     */
    private void setupUI(Context context, GenreListAdapter.OnGenreClickListener genreClickListener) {
        genreListAdapter = new GenreListAdapter(new ArrayList<>(), genreClickListener);
        dialogBinding.genreRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        dialogBinding.genreRecyclerView.setAdapter(genreListAdapter);
        dialogBinding.genreRecyclerView.setHasFixedSize(true);
        dialogBinding.closeMaterialCardView.setOnClickListener(v -> dialog.dismiss());
    }

    /**
     * Sets the data for the genre list and updates the UI.
     *
     * @param genres The list of genres to be displayed.
     */
    public void setData(List<Genre> genres) {
        genreListAdapter.setData(genres);
    }

    /**
     * Scrolls the genre list to the top position.
     */
    public void scrollOnTop() {
        dialogBinding.genreRecyclerView.smoothScrollToPosition(0);
    }

}