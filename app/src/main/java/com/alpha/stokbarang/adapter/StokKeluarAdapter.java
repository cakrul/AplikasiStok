package com.alpha.stokbarang.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.NumberFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.alpha.stokbarang.R;
import com.alpha.stokbarang.model.StokKeluar;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StokKeluarAdapter extends RecyclerView.Adapter<StokKeluarAdapter.StokKeluarHolder> {
    List<StokKeluar> semuastokkeluarlist;
    Context mContext;

    public StokKeluarAdapter(Context context, List<StokKeluar> stokKeluarLists){
        this.mContext = context;
        semuastokkeluarlist = stokKeluarLists;
    }

    @NonNull
    @Override
    public StokKeluarAdapter.StokKeluarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stok_keluar, parent, false);
        return new StokKeluarAdapter.StokKeluarHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StokKeluarAdapter.StokKeluarHolder holder, int position) {
        final StokKeluar semuastok = semuastokkeluarlist.get(position);
        holder.txt_s_nm_p.setText("Petugas Input :"+semuastok.getNm_pegawai());
        holder.txt_s_kd.setText(semuastok.getKd_produk());
        holder.txt_s_nm.setText(semuastok.getNm_produk());
        holder.txt_s_qty.setText("Stok Update : "+semuastok.getQty());
        holder.txt_s_wkt_in.setText("Waktu Input : "+semuastok.getWkt_keluar());
    }

    @Override
    public int getItemCount() {
        return semuastokkeluarlist.size();
    }

    public static class StokKeluarHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvSKNamaPegawai)
        TextView txt_s_nm_p;
        @BindView(R.id.tvSKNmProduk)
        TextView txt_s_nm;
        @BindView(R.id.tvSKkdProduk)
        TextView txt_s_kd;
        @BindView(R.id.tvSKQty)
        TextView txt_s_qty;
        @BindView(R.id.tvSKWaktuMasuk)
        TextView txt_s_wkt_in;

        public StokKeluarHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}
