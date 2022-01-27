package com.alpha.stokbarang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alpha.stokbarang.api.BaseApiService;
import com.alpha.stokbarang.api.UtilsApi;
import com.alpha.stokbarang.utils.SharedPrefManager;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutputActivity extends AppCompatActivity {

    @BindView(R.id.Kivqrcode)
    ImageView KimageView;
    @BindView(R.id.KetKdP)
    EditText KetKdP;
    @BindView(R.id.KetNamaP)
    EditText KetNamaP;
//    @BindView(R.id.KetHargaProduk)
//    EditText KetHargaP;
    @BindView(R.id.KetQty)
    EditText KetQtyP;
    @BindView(R.id.btnKeluarStok)
    Button btnKeluarStok;

    String kd,nm;

    ProgressDialog loading;
    BaseApiService mApiService;
    Context mContext;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Barang Keluar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Bind variable global
        ButterKnife.bind(this);

        mApiService = UtilsApi.getAPIService();
        mContext = this;
        //Mengambil data dari hasil scan produk
        Intent intent = getIntent();
        kd = intent.getStringExtra("kode");
        nm = intent.getStringExtra("nm");
        KetKdP.setText(kd);
        KetNamaP.setText(nm);

        sharedPrefManager = new SharedPrefManager(mContext);


        //redirect ke Scan QR code
        KimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent input =new Intent(OutputActivity.this, ScanKeluarActivity.class);
                startActivity(input);
            }
        });

        btnKeluarStok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestSimpanStokKeluar(
                        KetKdP.getText().toString(),
                        KetQtyP.getText().toString(),
                        sharedPrefManager.getSPNip()
                );
            }
        });
    }

    private void requestSimpanStokKeluar(String kd, String jml, String nip){
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

        mApiService.keluarStok(kd,jml,nip)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            new SweetAlertDialog(mContext)
                                    .setTitleText("Berhasil input stok keluar!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            startActivity(new Intent(mContext, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                        }
                                    })
                                    .show();

                        } else {
                            loading.dismiss();
                            new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Gagal!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            startActivity(new Intent(mContext, StokKeluarActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
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
                                        startActivity(new Intent(mContext, StokKeluarActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    }
                                })
                                .show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
}