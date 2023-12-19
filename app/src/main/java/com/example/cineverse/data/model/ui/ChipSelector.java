package com.example.cineverse.data.model.ui;

import com.google.android.material.chip.Chip;

public class ChipSelector {

    private final Chip chip;
    private final int position;
    private boolean visible;

    public ChipSelector(Chip chip, int position, boolean visible) {
        this.chip = chip;
        this.position = position;
        this.visible = visible;
    }

    public Chip getChip() {
        return chip;
    }

    public int getPosition() {
        return position;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
