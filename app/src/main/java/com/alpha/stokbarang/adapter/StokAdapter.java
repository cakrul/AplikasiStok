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
import com.alpha.stokbarang.model.Stok;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StokAdapter extends RecyclerView.Adapter<StokAdapter.StokHolder> {
    List<Stok> semuastoklist;
    Context mContext;

    public StokAdapter(Context context, List<Stok> stokLists){
        this.mContext = context;
        semuastoklist = stokLists;
    }

    @NonNull
    @Override
    public StokAdapter.StokHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stok, parent, false);
        return new StokAdapter.StokHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StokAdapter.StokHolder holder, int position) {
        final Stok semuastok = semuastoklist.get(position);
        holder.txt_s_nm_p.setText("Petugas Input :"+semuastok.getNm_pegawai());
        holder.txt_s_nm.setText(semuastok.getNm_produk());
        holder.txt_s_qty.setText("Stok Update : "+semuastok.getQty());
        holder.txt_s_wkt_in.setText("Waktu Input : "+semuastok.getWkt_masuk());
    }

    @Override
    public int getItemCount() {
        return semuastoklist.size();
    }

    public static class StokHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvSNamaPegawai)
        TextView txt_s_nm_p;
        @BindView(R.id.tvSNmProduk)
        TextView txt_s_nm;
        @BindView(R.id.tvSQty)
        TextView txt_s_qty;
        @BindView(R.id.tvSWaktuMasuk)
        TextView txt_s_wkt_in;

        public StokHolder(View itemView) {
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
