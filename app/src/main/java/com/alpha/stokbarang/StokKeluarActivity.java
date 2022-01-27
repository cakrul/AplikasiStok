package com.alpha.stokbarang;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alpha.stokbarang.adapter.StokKeluarAdapter;
import com.alpha.stokbarang.api.BaseApiService;
import com.alpha.stokbarang.api.UtilsApi;
import com.alpha.stokbarang.model.StokKeluar;
import com.alpha.stokbarang.model.StokKeluarResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StokKeluarActivity extends AppCompatActivity {

    @BindView(R.id.rvStokKeluar)
    RecyclerView rcvStokKeluar;
    ProgressDialog loading;

    Context mContext;
    List<StokKeluar> semuastokkeluarItemList = new ArrayList<>();
    StokKeluarAdapter stokKeluarAdapter;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok_keluar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent input =new Intent(StokKeluarActivity.this, OutputActivity.class);
                startActivity(input);
            }
        });

        ButterKnife.bind(this);

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        stokKeluarAdapter = new StokKeluarAdapter(this, semuastokkeluarItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rcvStokKeluar.setLayoutManager(mLayoutManager);
        rcvStokKeluar.setItemAnimator(new DefaultItemAnimator());

        getResultListStokKeluar();
    }

    private void getResultListStokKeluar(){
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

        mApiService.getStokKeluar().enqueue(new Callback<StokKeluarResponse>() {
            @Override
            public void onResponse(Call<StokKeluarResponse> call, Response<StokKeluarResponse> response) {
                if (response.isSuccessful()){
                    loading.dismiss();

                    final List<StokKeluar> semuaStokItems = response.body().getStokKeluarList();

                    rcvStokKeluar.setAdapter(new StokKeluarAdapter(mContext, semuaStokItems));
                    stokKeluarAdapter.notifyDataSetChanged();
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data list input stok pegawai", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StokKeluarResponse> call, Throwable t) {

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