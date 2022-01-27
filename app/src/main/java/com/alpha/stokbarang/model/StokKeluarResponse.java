package com.alpha.stokbarang.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StokKeluarResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<StokKeluar> stokKeluarList;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<StokKeluar> getStokKeluarList() {
        return stokKeluarList;
    }

    public void setStokKeluarList(List<StokKeluar> stokKeluarList) {
        this.stokKeluarList = stokKeluarList;
    }

    @Override
    public String toString(){
        return
                "{" +
                        "data = '" + stokKeluarList + '\'' +
                        ",success = '" + success + '\'' +
                        "}";
    }
}
