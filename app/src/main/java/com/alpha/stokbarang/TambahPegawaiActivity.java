package com.alpha.stokbarang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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

public class TambahPegawaiActivity extends AppCompatActivity {

    @BindView(R.id.tieEmail)
    TextInputEditText tieEmail;
    @BindView(R.id.errorEmail)
    TextInputLayout errorEmail;
    @BindView(R.id.tieNamaP)
    TextInputEditText tieNamaP;
    @BindView(R.id.errorNama)
    TextInputLayout errorNama;
    @BindView(R.id.tieNoHp)
    TextInputEditText tieNoHp;
    @BindView(R.id.errorNohp)
    TextInputLayout errorNohp;
    @BindView(R.id.tiePassP)
    TextInputEditText tiePassP;
    @BindView(R.id.errorPass)
    TextInputLayout errorPass;
    @BindView(R.id.rbPosisi)
    RadioGroup rbPosisi;
    @BindView(R.id.btnSimpanPegawai)
    Button btnSimpanPegawai;

    RadioButton selectedRadioButton;
    ProgressDialog loading;
    BaseApiService mApiService;
    Context mContext;

    boolean isNameValid, isEmailValid, isPhoneValid, isPasswordValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pegawai);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Input Data Pegawai");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Bind variable global
        ButterKnife.bind(this);
        mApiService = UtilsApi.getAPIService();
        mContext = this;

        btnSimpanPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAllFields();
            }
        });

    }

    private void CheckAllFields() {

        // Check for a valid name.
        if (tieEmail.getText().toString().isEmpty()) {
            errorEmail.setError("Masukan Email");
            isEmailValid = false;
        } else  {
            isEmailValid = true;
            errorEmail.setErrorEnabled(false);
        }

        if (tieNamaP.getText().toString().isEmpty()) {
            errorNama.setError("Masukan Nama");
            isNameValid = false;
        } else  {
            isNameValid = true;
            errorNama.setErrorEnabled(false);
        }

        if (tieNoHp.getText().toString().isEmpty()) {
            errorNohp.setError("Masukan Nomor Telepon");
            isPhoneValid = false;
        } else  {
            isPhoneValid = true;
            errorNohp.setErrorEnabled(false);
        }

        // Check for a valid password.
        if (tiePassP.getText().toString().isEmpty()) {
            errorPass.setError("Masukan Password");
            isPasswordValid = false;
        } else if (tiePassP.getText().length() < 6) {
            errorPass.setError("Masukan Password Lebih dari 6");
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
            errorPass.setErrorEnabled(false);
        }

        if (isNameValid && isEmailValid && isPhoneValid && isPasswordValid) {
            int selectedRadioButtonId = rbPosisi.getCheckedRadioButtonId();
            if (selectedRadioButtonId != -1) {
                selectedRadioButton = findViewById(selectedRadioButtonId);
                String selectedRbText = selectedRadioButton.getText().toString();
                requestSimpanPegawai(Objects.requireNonNull(tieNamaP.getText()).toString(),
                        Objects.requireNonNull(tieNoHp.getText()).toString(),
                        selectedRbText,
                        Objects.requireNonNull(tieEmail.getText()).toString(),
                        Objects.requireNonNull(tiePassP.getText()).toString());
            }else{
                Toast.makeText(mContext, "Pilih posisi dulu", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestSimpanPegawai(String nm, String no_hp, String posisi, String email, String pwd){
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

        mApiService.tambahPegawai(nm,no_hp,posisi,email,pwd)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            new SweetAlertDialog(mContext)
                                    .setTitleText("Berhasil menambahkan Pegawai!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            startActivity(new Intent(mContext, PegawaiActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                        }
                                    })
                                    .show();

                        } else {
                            loading.dismiss();
                            new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Gagal menambahkan Pegawai!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            startActivity(new Intent(mContext, PegawaiActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
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
        startActivity(new Intent(mContext, PegawaiActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}