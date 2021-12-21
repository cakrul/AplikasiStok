package com.alpha.stokbarang.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("nip")
    Integer nip;
    @SerializedName("nm_pegawai")
    String nm_pegawai;
    @SerializedName("no_hp")
    String no_hp;
    @SerializedName("email")
    String email;
    @SerializedName("posisi")
    String posisi;
    @SerializedName("password")
    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getNip() {
        return nip;
    }

    public void setNip(Integer nip) {
        this.nip = nip;
    }

    public String getNm_pegawai() {
        return nm_pegawai;
    }

    public void setNm_pegawai(String nm_pegawai) {
        this.nm_pegawai = nm_pegawai;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    @Override
    public String toString(){
        return
                "data{" +
                        "nip = '" + nip + '\'' +
                        ",nm_pegawai = '" + nm_pegawai + '\'' +
                        ",no_hp = '" + no_hp + '\'' +
                        ",posisi = '" + posisi + '\'' +
                        ",email = '" + email + '\'' +
                        ",password = '" + password + '\'' +
                        "}";
    }
}
