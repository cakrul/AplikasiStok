package com.alpha.stokbarang.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alpha.stokbarang.PegawaiActivity;
import com.alpha.stokbarang.R;
import com.alpha.stokbarang.api.BaseApiService;
import com.alpha.stokbarang.model.ProdukList;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.BarangHolder>{

    List<ProdukList> semuaprodukLists;
    Context mContext;

    BaseApiService mApiService;
    ProgressDialog loading;

    public BarangAdapter(Context context, List<ProdukList> produkLists){
        this.mContext = context;
        semuaprodukLists = produkLists;
    }

    @NonNull
    @Override
    public BarangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        return new BarangHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BarangHolder holder, int position) {
        final ProdukList semuaproduk = semuaprodukLists.get(position);
        holder.tvNamaBarang.setText(semuaproduk.getNm_produk());
        holder.tvHarga.setText(formatRupiah(semuaproduk.getHarga_jual()));
        holder.tvStok.setText("Stok : "+semuaproduk.getStok());
        holder.tvUpdated.setText("Update Terakhir "+semuaproduk.getUpdated_at());
        holder.tvKdBarang.setText("Kode Produk : "+semuaproduk.getKd_produk());
        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Hapus Produk "+semuaproduk.getKd_produk() + "?")
                        .setContentText("Anda yakin ingin menghapus produk ini!")
                        .setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setConfirmButton("Hapus",new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                requestDeleteProduk(semuaproduk.getKd_produk());
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return semuaprodukLists.size();
    }

    public static class BarangHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNamaBarang)
        TextView tvNamaBarang;
        @BindView(R.id.tvHarga)
        TextView tvHarga;
        @BindView(R.id.tvStok)
        TextView tvStok;
        @BindView(R.id.tvUpdated)
        TextView tvUpdated;
        @BindView(R.id.tvKdBarang)
        TextView tvKdBarang;
        @BindView(R.id.btnHapusProduk)
        ImageButton hapus;

        public BarangHolder(View itemView) {
            super(itemView);
            
            ButterKnife.bind(this, itemView);
        }

    }

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    private void requestDeleteProduk(String kd_produk_nya){
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

        mApiService.hapusProduk(kd_produk_nya).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
