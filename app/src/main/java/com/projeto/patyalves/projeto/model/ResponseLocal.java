package com.projeto.patyalves.projeto.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abceducation on 09/09/17.
 */

public class ResponseLocal {
    @SerializedName("local")
    private List<Local> locais;

    public List<Local> getLocais() {
        return locais;
    }

    public void setLocais(List<Local> locais) {
        this.locais = locais;
    }


}
