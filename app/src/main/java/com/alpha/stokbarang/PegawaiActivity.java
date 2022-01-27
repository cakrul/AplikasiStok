package com.alpha.stokbarang;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alpha.stokbarang.adapter.PegawaiAdapter;
import com.alpha.stokbarang.api.BaseApiService;
import com.alpha.stokbarang.api.UtilsApi;
import com.alpha.stokbarang.model.User;
import com.alpha.stokbarang.model.UserResponse;
import com.alpha.stokbarang.utils.Constant;
import com.alpha.stokbarang.utils.RecyclerItemClickListener;
import com.alpha.stokbarang.utils.SwipeToDeleteCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class PegawaiActivity extends AppCompatActivity {

    @BindView(R.id.rvPegawai)
    RecyclerView rvPegawai;
    ProgressDialog loading;

    Context mContext;
    List<User> semuauserList = new ArrayList<>();
    PegawaiAdapter pegawaiAdapter;
    BaseApiService mApiService;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Data Pegawai");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tambahPegawai = new Intent(mContext, TambahPegawaiActivity.class);
                startActivity(tambahPegawai);
            }
        });

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        pegawaiAdapter = new PegawaiAdapter(this, semuauserList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvPegawai.setLayoutManager(mLayoutManager);
        rvPegawai.setItemAnimator(new DefaultItemAnimator());

        getResultListUser();
        enableSwipeToDeleteAndUndo();
    }

    private void getResultListUser(){
        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);

        mApiService.getUsers().enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful()){
                    loading.dismiss();

                    final List<User> semuausers = response.body().getSemuausers();

                    rvPegawai.setAdapter(new PegawaiAdapter(mContext, semuausers));
                    pegawaiAdapter.notifyDataSetChanged();

                    initDataIntent(semuausers);
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data User", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDataIntent(final List<User> userList){
        rvPegawai.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        String nip = String.valueOf(userList.get(position).getNip());
                        String nama = userList.get(position).getNm_pegawai();
                        String nohp = userList.get(position).getNo_hp();
                        String posisi = userList.get(position).getPosisi();
                        String email = userList.get(position).getEmail();
                        String pwd = userList.get(position).getPassword();

                        Intent detailUser = new Intent(mContext, DetailPegawaiActivity.class);
                        detailUser.putExtra(Constant.KEY_NIP_P, nip);
                        detailUser.putExtra(Constant.KEY_NAMA_P, nama);
                        detailUser.putExtra(Constant.KEY_NOHP_P, nohp);
                        detailUser.putExtra(Constant.KEY_POSISI_P, posisi);
                        detailUser.putExtra(Constant.KEY_EMAIL_P, email);
                        detailUser.putExtra(Constant.KEY_PWD_P, pwd);
                        startActivity(detailUser);
                    }
                }));
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(mContext) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                pegawaiAdapter.removeItem(position);

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rvPegawai);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
}