package com.alpha.stokbarang.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProduklistResponse {
    @SerializedName("data")
    private List<ProdukList> semuaproduk;

    @SerializedName("success")
    private boolean success;

    public void setSemuaproduk(List<ProdukList> semuaproduk){
        this.semuaproduk = semuaproduk;
    }

    public List<ProdukList> getSemuaproduk(){
        return semuaproduk;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString(){
        return
                "{" +
                        "data = '" + semuaproduk + '\'' +
                        ",success = '" + success + '\'' +
                        "}";
    }


}
