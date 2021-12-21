package com.alpha.stokbarang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.input)
    LinearLayout llInput;
    @BindView(R.id.barang)
    LinearLayout barang;
    @BindView(R.id.pegawai)
    LinearLayout pegawai;
    @BindView(R.id.ivProfil)
    ImageView ivProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

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



    }
}