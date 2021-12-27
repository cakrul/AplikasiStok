package com.alpha.stokbarang.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StokResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<Stok> semuastok;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Stok> getSemuastok() {
        return semuastok;
    }

    public void setSemuastok(List<Stok> semuastok) {
        this.semuastok = semuastok;
    }

    @Override
    public String toString(){
        return
                "{" +
                        "data = '" + semuastok + '\'' +
                        ",success = '" + success + '\'' +
                        "}";
    }
}
