package com.alpha.stokbarang.model;

import com.google.gson.annotations.SerializedName;

public class ProdukList {

    @SerializedName("kd_produk")
    private String kd_produk;
    @SerializedName("nm_produk")
    private String nm_produk;
    @SerializedName("harga_jual")
    private String harga_jual;
    @SerializedName("stok")
    private String stok;
    @SerializedName("updated_at")
    private String updated_at;

    public String getKd_produk() {
        return kd_produk;
    }

    public void setKd_produk(String kd_produk) {
        this.kd_produk = kd_produk;
    }

    public String getNm_produk() {
        return nm_produk;
    }

    public void setNm_produk(String nm_produk) {
        this.nm_produk = nm_produk;
    }

    public Double getHarga_jual() {
        return Double.valueOf(harga_jual);
    }

    public void setHarga_jual(String harga_jual) {
        this.harga_jual = harga_jual;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString(){
        return
                "data{" +
                        "kd_produk = '" + kd_produk + '\'' +
                        ",nm_produk = '" + nm_produk + '\'' +
                        ",harga_jual = '" + harga_jual + '\'' +
                        ",stok = '" + stok + '\'' +
                        ",updated_at = '" + updated_at + '\'' +
                        "}";
    }

}
