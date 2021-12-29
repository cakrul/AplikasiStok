package com.alpha.stokbarang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Objects;

public class DetailProdukActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Detail Produk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}