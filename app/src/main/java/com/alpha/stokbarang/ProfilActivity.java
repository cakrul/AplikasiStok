package com.alpha.stokbarang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.stokbarang.utils.SharedPrefManager;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfilActivity extends AppCompatActivity {

    @BindView(R.id.btnKeluar)
    Button btnKeluar;
    @BindView(R.id.txt_p_nm)
    TextView txtNama;
    @BindView(R.id.txt_p_posisi)
    TextView txtPosisi;
    @BindView(R.id.txt_p_email)
    TextView txtEmail;
    @BindView(R.id.txt_p_nohp)
    TextView txtNohp;


    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Profil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        sharedPrefManager = new SharedPrefManager(this);
        txtNama.setText(sharedPrefManager.getSPNama());
        txtPosisi.setText(sharedPrefManager.getSPPosisi());
        txtEmail.setText(sharedPrefManager.getSPEmail());
        txtNohp.setText(sharedPrefManager.getSPNohp());


        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(ProfilActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
    }
}