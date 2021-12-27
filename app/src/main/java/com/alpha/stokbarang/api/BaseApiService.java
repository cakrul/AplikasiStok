package com.alpha.stokbarang.api;
import com.alpha.stokbarang.model.ProduklistResponse;
import com.alpha.stokbarang.model.StokResponse;
import com.alpha.stokbarang.model.UserResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BaseApiService {

    @FormUrlEncoded
    @POST("pegawai/login")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("pegawai/tambah")
    Call<ResponseBody> tambahPegawai(@Field("txt_nm_pegawai") String nm_pegawai,
                                       @Field("txt_no_hp") String nohp,
                                       @Field("posisi") String posisi,
                                       @Field("txt_email") String email,
                                       @Field("txt_password") String pwd);

    @FormUrlEncoded
    @POST("stok/tambah")
    Call<ResponseBody> tambahStok(@Field("kd_produk") String kd_produk,
                                  @Field("txt_harga") String txt_harga,
                                  @Field("txt_qty") String txt_qty,
                                  @Field("txt_nip") String txt_nip);

    @FormUrlEncoded
    @POST("produk/tambah")
    Call<ResponseBody> tambahProduk(@Field("txt_nm_produk") String txt_nm_produk,
                                  @Field("txt_harga_jual") String txt_harga_jual,
                                  @Field("txt_stok") String txt_stok);

    @GET("produk")
    Call<ProduklistResponse> getProduks();

    @GET("pegawai")
    Call<UserResponse> getUsers();

    @GET("stok")
    Call<StokResponse> getStok();

    @GET("produk/scan/{kode}")
    Call<ResponseBody> getScan(@Path("kode") String kode);




}
