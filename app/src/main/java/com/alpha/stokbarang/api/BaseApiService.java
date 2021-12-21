package com.alpha.stokbarang.api;
import com.alpha.stokbarang.model.ProdukList;
import com.alpha.stokbarang.model.ProduklistResponse;
import com.alpha.stokbarang.model.User;
import com.alpha.stokbarang.model.UserResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BaseApiService {

    @FormUrlEncoded
    @POST("pegawai/login")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("pegawai/tambah")
    Call<ResponseBody> registerRequest(@Field("txt_nm_pegawai") String nm_pegawai,
                                       @Field("txt_no_hp") String nohp,
                                       @Field("posisi") String posisi,
                                       @Field("txt_email") String email,
                                       @Field("txt_password") String pwd);

    @GET("produk")
    Call<ProduklistResponse> getProduks();

    @GET("pegawai")
    Call<UserResponse> getUsers();
}
