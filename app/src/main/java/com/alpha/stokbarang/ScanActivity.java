package com.alpha.stokbarang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alpha.stokbarang.api.BaseApiService;
import com.alpha.stokbarang.api.UtilsApi;
import com.alpha.stokbarang.utils.Constant;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanActivity extends AppCompatActivity {

    ProgressDialog loading;
    BaseApiService mApiService;
    Context mContext;

    private CodeScanner mCodeScanner;
    private int CAMERA_PERMISSION_CODE = 10232;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Scan Stok Masuk");

        mApiService = UtilsApi.getAPIService();
        mContext = this;
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(mContext, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String getResult = result.getText();
                        getResultScanBarang(getResult);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }


    private void requestPermisionCamera(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(ScanActivity.this, Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(ScanActivity.this)
                    .setTitle("Perlu Izin Aplikasi")
                    .setMessage("Perlu menyetujui penggunaan Kamera untuk menggunakan Scan QR")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ScanActivity.this,new String[] {Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(ScanActivity.this,new String[] {Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);
        }
    }

    private void getResultScanBarang(String kd_produk){
        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);

        mApiService.getScan(kd_produk).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    loading.dismiss();

                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        Log.d("statusnya apa", jsonRESULTS.getString("success"));
                        if (jsonRESULTS.getString("success").equals("true")){

                            String kd_produk = jsonRESULTS.getJSONObject("data").getString("kd_produk");
                            String nm_produk = jsonRESULTS.getJSONObject("data").getString("nm_produk");

                            Intent hasilScan = new Intent(ScanActivity.this, InputStokActivity.class);
                            hasilScan.putExtra("kode", kd_produk);
                            hasilScan.putExtra("nm", nm_produk);
                            startActivity(hasilScan);
                            finish();

                        } else {
                            String error_message = jsonRESULTS.getString("message");
                            Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    loading.dismiss();
                    new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Gagal mencari barang!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    startActivity(new Intent(mContext, InputStokActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext, "Izin Camera Terpenuhi", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(mContext, "Perlu izin Kamera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            mCodeScanner.startPreview();
        }else{
            requestPermisionCamera();
        }
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}