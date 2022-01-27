package com.alpha.stokbarang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alpha.stokbarang.api.BaseApiService;
import com.alpha.stokbarang.api.UtilsApi;
import com.alpha.stokbarang.utils.Constant;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProdukActivity extends AppCompatActivity {

    @BindView(R.id.etkd_produk)
    TextInputEditText kd;
    @BindView(R.id.etNm_produk)
    TextInputEditText nm;
    @BindView(R.id.etHarga_jual)
    TextInputEditText hrg;
    @BindView(R.id.etStok)
    TextInputEditText stk;
    @BindView(R.id.btnHapusProduk)
    Button btnH;

    String kd_produk,nm_produk,harga,stok;

    Context mContext;
    BaseApiService mApiService;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Detail Produk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService();

        Intent intent = getIntent();
        kd_produk = intent.getStringExtra(Constant.KEY_KD_PRODUK);
        nm_produk = intent.getStringExtra(Constant.KEY_NM_PRODUK);
        harga = intent.getStringExtra(Constant.KEY_HARGA_P);
        stok = intent.getStringExtra(Constant.KEY_STOK_P);

        kd.setText(kd_produk);
        nm.setText(nm_produk);
        hrg.setText(harga);
        stk.setText(stok);

        btnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Hapus Produk?")
                        .setContentText("Anda yakin ingin menghapus produk ini!")
                        .setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setConfirmButton("Hapus",new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                requestDeleteProduk(kd_produk);
                            }
                        })
                        .show();
            }
        });
    }

    private void requestDeleteProduk(String kd_produk_nya){
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

        mApiService.hapusProduk(kd_produk_nya).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    startActivity(new Intent(mContext, PegawaiActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, ProdukActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}