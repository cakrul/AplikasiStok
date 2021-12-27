package com.alpha.stokbarang.model;

import com.google.gson.annotations.SerializedName;

public class Stok {

    @SerializedName("id_masuk")
    private String id_masuk;
    @SerializedName("kd_produk")
    private String kd_produk;
    @SerializedName("nip_by")
    private String nip_by;
    @SerializedName("qty")
    private String qty;
    @SerializedName("harga")
    private String harga;
    @SerializedName("wkt_masuk")
    private String wkt_masuk;
    @SerializedName("nm_pegawai")
    private String nm_pegawai;
    @SerializedName("nm_produk")
    private String nm_produk;

    public String getId_masuk() {
        return id_masuk;
    }

    public void setId_masuk(String id_masuk) {
        this.id_masuk = id_masuk;
    }

    public String getKd_produk() {
        return kd_produk;
    }

    public void setKd_produk(String kd_produk) {
        this.kd_produk = kd_produk;
    }

    public String getNip_by() {
        return nip_by;
    }

    public void setNip_by(String nip_by) {
        this.nip_by = nip_by;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getWkt_masuk() {
        return wkt_masuk;
    }

    public void setWkt_masuk(String wkt_masuk) {
        this.wkt_masuk = wkt_masuk;
    }

    public String getNm_pegawai() {
        return nm_pegawai;
    }

    public void setNm_pegawai(String nm_pegawai) {
        this.nm_pegawai = nm_pegawai;
    }

    public String getNm_produk() {
        return nm_produk;
    }

    public void setNm_produk(String nm_produk) {
        this.nm_produk = nm_produk;
    }
}
