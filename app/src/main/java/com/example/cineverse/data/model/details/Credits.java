package com.example.cineverse.data.model.details;

import java.util.List;

public class Credits {

    private List<Cast> cast;

    public Credits(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

}
