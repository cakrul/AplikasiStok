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
import com.alpha.stokbarang.utils.Constant;
import com.alpha.stokbarang.utils.RecyclerItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        Objects.requireNonNull(getSupportActionBar()).setTitle("Data Barang");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        barangAdapter = new BarangAdapter(this, semuaprodukItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rcvBarang.setLayoutManager(mLayoutManager);
        rcvBarang.setItemAnimator(new DefaultItemAnimator());

        getResultListBarang();

        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tambah =new Intent(ProdukActivity.this, TambahProdukActivity.class);
                startActivity(tambah);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Pencaharian Produk...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                getResultListBarangCari(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getResultListBarangCari(newText);
                return false;
            }


        });

        return super.onCreateOptionsMenu(menu);
    }

    private void getResultListBarangCari(String cari_produk){

        mApiService.getCariProduk(cari_produk).enqueue(new Callback<ProduklistResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProduklistResponse> call, @NonNull Response<ProduklistResponse> response) {
                if (response.isSuccessful()){

                    final List<ProdukList> semuaBarangItems = response.body().getSemuaproduk();

                    rcvBarang.setAdapter(new BarangAdapter(mContext, semuaBarangItems));
                    barangAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(mContext, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProduklistResponse> call, Throwable t) {
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
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

                    //initDataIntent(semuaprodukItemList);
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProduklistResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDataIntent(final List<ProdukList> produkListList){
        rcvBarang.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        final ProdukList semuaproduk = produkListList.get(position);

                        String k = String.valueOf(semuaproduk.getKd_produk());
                        String n = semuaproduk.getNm_produk();
                        Double h = semuaproduk.getHarga_jual();
                        String s = semuaproduk.getStok();

                        Intent detailUser = new Intent(mContext, DetailProdukActivity.class);
                        detailUser.putExtra(Constant.KEY_KD_PRODUK_P, k);
                        detailUser.putExtra(Constant.KEY_NM_PRODUK_P, n);
                        detailUser.putExtra(Constant.KEY_HARGA_P, h);
                        detailUser.putExtra(Constant.KEY_STOK_P, s);
                        startActivity(detailUser);
                    }
                }));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
}
