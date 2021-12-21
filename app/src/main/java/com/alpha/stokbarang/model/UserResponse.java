package com.alpha.stokbarang.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<User> semuausers;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<User> getSemuausers() {
        return semuausers;
    }

    public void setSemuausers(List<User> semuausers) {
        this.semuausers = semuausers;
    }

    @Override
    public String toString(){
        return
                "{" +
                        "data = '" + semuausers + '\'' +
                        ",success = '" + success + '\'' +
                        "}";
    }
}
