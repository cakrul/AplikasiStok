package com.alpha.stokbarang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alpha.stokbarang.api.BaseApiService;
import com.alpha.stokbarang.api.UtilsApi;
import com.alpha.stokbarang.utils.Constant;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

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
    @BindView(R.id.btnHapusPegawai)
    Button btnHapusPegawai;

    ProgressDialog loading;

    String Mnip;
    String Mnama;
    String Mnohp;
    String Mposisi;
    String Memail;

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

        etNip.setText(Mnip);
        etNama.setText(Mnama);
        etNohp.setText(Mnohp);
        etPosisi.setText(Mposisi);
        etEmail.setText(Memail);

        btnHapusPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Hapus Pegawai?")
                        .setContentText("Anda yakin ingin menghapus pegawai ini!")
                        .setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setConfirmButton("Hapus",new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.setTitleText("Terhapus!")
                                        .setContentText("Pegawai sukses terhapus!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
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