package com.alpha.stokbarang;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alpha.stokbarang.adapter.BarangAdapter;
import com.alpha.stokbarang.api.BaseApiService;
import com.alpha.stokbarang.api.UtilsApi;
import com.alpha.stokbarang.model.ProdukList;
import com.alpha.stokbarang.model.ProduklistResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukActivity extends AppCompatActivity {

    @BindView(R.id.rvBarang)
    RecyclerView rcvBarang;
    ProgressDialog loading;

    Context mContext;
    List<ProdukList> semuaprodukItemList = new ArrayList<>();
    BarangAdapter barangAdapter;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        barangAdapter = new BarangAdapter(this, semuaprodukItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rcvBarang.setLayoutManager(mLayoutManager);
        rcvBarang.setItemAnimator(new DefaultItemAnimator());

        getResultListBarang();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tambah =new Intent(ProdukActivity.this, TambahProdukActivity.class);
                startActivity(tambah);
            }
        });
    }

    private void getResultListBarang(){
        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);

        mApiService.getProduks().enqueue(new Callback<ProduklistResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProduklistResponse> call, @NonNull Response<ProduklistResponse> response) {
                if (response.isSuccessful()){
                    loading.dismiss();

                    final List<ProdukList> semuaBarangItems = response.body().getSemuaproduk();

                    rcvBarang.setAdapter(new BarangAdapter(mContext, semuaBarangItems));
                    barangAdapter.notifyDataSetChanged();
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProduklistResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
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
