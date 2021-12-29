package com.alpha.stokbarang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alpha.stokbarang.api.BaseApiService;
import com.alpha.stokbarang.api.UtilsApi;
import com.alpha.stokbarang.utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputStokActivity extends AppCompatActivity {

    @BindView(R.id.ivqrcode)
    ImageView imageView;
    @BindView(R.id.etKdP)
    EditText etKdP;
    @BindView(R.id.etNamaP)
    EditText etNamaP;
    @BindView(R.id.etHargaProduk)
    EditText etHargaP;
    @BindView(R.id.etQty)
    EditText etQtyP;
    @BindView(R.id.btnTambahStok)
    Button btnTambahStok;

    String kd,nm;

    ProgressDialog loading;
    BaseApiService mApiService;
    Context mContext;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_stok);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Input Stok Barang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Bind variable global
        ButterKnife.bind(this);

        mApiService = UtilsApi.getAPIService();
        mContext = this;
        //Mengambil data dari hasil scan produk
        Intent intent = getIntent();
        kd = intent.getStringExtra("kode");
        nm = intent.getStringExtra("nm");
        etKdP.setText(kd);
        etNamaP.setText(nm);

        sharedPrefManager = new SharedPrefManager(mContext);


        //redirect ke Scan QR code
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent input =new Intent(InputStokActivity.this, ScanActivity.class);
                startActivity(input);
            }
        });

        btnTambahStok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestSimpanStok(
                        etKdP.getText().toString(),
                        etHargaP.getText().toString(),
                        etQtyP.getText().toString(),
                        sharedPrefManager.getSPNip()
                );
            }
        });
    }

    private void requestSimpanStok(String kd, String hrg, String jml, String nip){
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

        mApiService.tambahStok(kd,hrg,jml,nip)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            new SweetAlertDialog(mContext)
                                    .setTitleText("Berhasil menambahkan data stok barang!")
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
                                    .setContentText("Gagal menambahkan data stok barang!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            startActivity(new Intent(mContext, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
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
                                        startActivity(new Intent(mContext, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
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