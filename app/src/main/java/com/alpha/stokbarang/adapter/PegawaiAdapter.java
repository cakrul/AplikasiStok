package com.alpha.stokbarang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alpha.stokbarang.R;
import com.alpha.stokbarang.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PegawaiAdapter extends RecyclerView.Adapter<PegawaiAdapter.PegawaiHolder>{

    List<User> semuauserLists;
    Context mContext;

    public PegawaiAdapter(Context context, List<User> userLists){
        this.mContext = context;
        semuauserLists = userLists;
    }

    @NonNull
    @Override
    public PegawaiAdapter.PegawaiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pegawai, parent, false);
        return new PegawaiAdapter.PegawaiHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PegawaiAdapter.PegawaiHolder holder, int position) {
        final User semuauser = semuauserLists.get(position);
        holder.tvNama.setText(semuauser.getNm_pegawai());
        holder.tvPosisi.setText(semuauser.getPosisi());
    }

    @Override
    public int getItemCount() {
        return semuauserLists.size();
    }

    public static class PegawaiHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvNama)
        TextView tvNama;
        @BindView(R.id.tvPosisi)
        TextView tvPosisi;

        public PegawaiHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }
}
