package com.alpha.stokbarang;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alpha.stokbarang.utils.SharedPrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.input)
    LinearLayout llInput;
    @BindView(R.id.barang)
    LinearLayout barang;
    @BindView(R.id.pegawai)
    LinearLayout pegawai;
    @BindView(R.id.liststok)
    LinearLayout liststok;
    @BindView(R.id.ivProfil)
    ImageView ivProfil;
    @BindView(R.id.text_nama_user)
    TextView txtNamaUser;

    SharedPrefManager sharedPrefManager;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        sharedPrefManager = new SharedPrefManager(this);
        txtNamaUser.setText(sharedPrefManager.getSPNama());

        llInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent input =new Intent(MainActivity.this, InputStokActivity.class);
                startActivity(input);
            }
        });

        barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home=new Intent(MainActivity.this, ProdukActivity.class);
                startActivity(home);
            }
        });

        pegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pegawai = new Intent(MainActivity.this, PegawaiActivity.class);
                startActivity(pegawai);
            }
        });

        ivProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profil = new Intent(MainActivity.this, ProfilActivity.class);
                startActivity(profil);
            }
        });

        liststok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent liststok = new Intent(MainActivity.this, StokActivity.class);
                startActivity(liststok);
            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan 2 kali untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}