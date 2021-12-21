package com.alpha.stokbarang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alpha.stokbarang.api.BaseApiService;
import com.alpha.stokbarang.api.UtilsApi;
import com.alpha.stokbarang.utils.Constant;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailPegawaiActivity extends AppCompatActivity {

    @BindView(R.id.etNip)
    TextInputEditText etNip;
    @BindView(R.id.etNama)
    TextInputEditText etNama;
    @BindView(R.id.etNohp)
    TextInputEditText etNohp;
    @BindView(R.id.etPosisi)
    TextInputEditText etPosisi;
    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etPwd)
    TextInputEditText etPwd;

    ProgressDialog loading;

    String Mnip;
    String Mnama;
    String Mnohp;
    String Mposisi;
    String Memail;
    String Mpwd;

    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pegawai);
        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService();

        Intent intent = getIntent();
        Mnip = intent.getStringExtra(Constant.KEY_NIP_P);
        Mnama = intent.getStringExtra(Constant.KEY_NAMA_P);
        Mnohp = intent.getStringExtra(Constant.KEY_NOHP_P);
        Mposisi = intent.getStringExtra(Constant.KEY_POSISI_P);
        Memail = intent.getStringExtra(Constant.KEY_EMAIL_P);
        Mpwd = intent.getStringExtra(Constant.KEY_PWD_P);

        etNip.setText(Mnip);
        etNama.setText(Mnama);
        etNohp.setText(Mnohp);
        etPosisi.setText(Mposisi);
        etEmail.setText(Memail);
        etPwd.setText(Mpwd);
    }
}