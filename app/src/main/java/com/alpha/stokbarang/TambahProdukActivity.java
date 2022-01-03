package com.alpha.stokbarang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alpha.stokbarang.api.BaseApiService;
import com.alpha.stokbarang.api.UtilsApi;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahProdukActivity extends AppCompatActivity {

    @BindView(R.id.btnSimpanProduk)
    Button btnSimpan;
    @BindView(R.id.etNamaProduk)
    TextInputEditText tieNm;
    @BindView(R.id.etHargaProduk)
    TextInputEditText tieHarga;
    @BindView(R.id.etStokProduk)
    TextInputEditText tieStok;

    @BindView(R.id.tiNm)
    TextInputLayout tiNm;
    @BindView(R.id.tiStok)
    TextInputLayout tiStok;
    @BindView(R.id.tiHarga)
    TextInputLayout tiHarga;


    ProgressDialog loading;
    BaseApiService mApiService;
    Context mContext;

    boolean isNm, isStok, isHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_produk);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Input Data Barang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Bind variable global
        ButterKnife.bind(this);

        mApiService = UtilsApi.getAPIService();
        mContext = this;

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAllFields();
            }
        });
    }

    private void CheckAllFields() {
        if (tieNm.getText().toString().isEmpty()){
            tiNm.setError("Nama Produk Kosong!");
            isNm = false;
        }else{
            isNm = true;
            tiNm.setErrorEnabled(false);
        }

        if (tieStok.getText().toString().isEmpty()){
            tiStok.setError("Nama Produk Kosong!");
            isStok = false;
        }else{
            isStok = true;
            tiStok.setErrorEnabled(false);
        }

        if (tieHarga.getText().toString().isEmpty()){
            tiHarga.setError("Nama Produk Kosong!");
            isHarga = false;
        }else{
            isHarga = true;
            tiHarga.setErrorEnabled(false);
        }

        if (isNm && isStok && isHarga) {
            requestSimpanProduk(tieNm.getText().toString(),tieHarga.getText().toString(),tieStok.getText().toString());
        }
    }

    private void requestSimpanProduk(String nm, String hrg, String jml){
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

        mApiService.tambahProduk(nm,hrg,jml)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            new SweetAlertDialog(mContext)
                                    .setTitleText("Berhasil menambahkan data barang!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            startActivity(new Intent(mContext, ProdukActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                        }
                                    })
                                    .show();

                        } else {
                            loading.dismiss();
                            new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Gagal menambahkan data barang!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            startActivity(new Intent(mContext, ProdukActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        loading.dismiss();
                        new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Koneksi internet bermasalah!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        startActivity(new Intent(mContext, ProdukActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    }
                                })
                                .show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, ProdukActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}