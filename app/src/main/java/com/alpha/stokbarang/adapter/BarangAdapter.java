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
import com.alpha.stokbarang.model.ProdukList;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.BarangHolder>{

    List<ProdukList> semuaprodukLists;
    Context mContext;

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
}
