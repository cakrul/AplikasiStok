package com.alpha.stokbarang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.alpha.stokbarang.adapter.StokAdapter;
import com.alpha.stokbarang.api.BaseApiService;
import com.alpha.stokbarang.api.UtilsApi;
import com.alpha.stokbarang.model.Stok;
import com.alpha.stokbarang.model.StokResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StokActivity extends AppCompatActivity {

    @BindView(R.id.rvStok)
    RecyclerView rcvStok;
    ProgressDialog loading;

    Context mContext;
    List<Stok> semuastokItemList = new ArrayList<>();
    StokAdapter stokAdapter;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Data Stok Masuk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        stokAdapter = new StokAdapter(this, semuastokItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rcvStok.setLayoutManager(mLayoutManager);
        rcvStok.setItemAnimator(new DefaultItemAnimator());

        getResultListStok();
    }

    private void getResultListStok(){
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

        mApiService.getStok().enqueue(new Callback<StokResponse>() {
            @Override
            public void onResponse(@NonNull Call<StokResponse> call, @NonNull Response<StokResponse> response) {
                if (response.isSuccessful()){
                    loading.dismiss();

                    final List<Stok> semuaStokItems = response.body().getSemuastok();

                    rcvStok.setAdapter(new StokAdapter(mContext, semuaStokItems));
                    stokAdapter.notifyDataSetChanged();
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data list input stok pegawai", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<StokResponse> call, @NonNull Throwable t) {
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