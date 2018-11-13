package id.co.tpcc.drypediaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import id.co.tpcc.drypediaapp.DetailTokoActivity;
import id.co.tpcc.drypediaapp.ItemTokoActivity;
import id.co.tpcc.drypediaapp.R;
import id.co.tpcc.drypediaapp.model.TokoResult;

public class TokoAdapter extends RecyclerView.Adapter<TokoAdapter.ViewHolder> {
    Context context;
    List<TokoResult> tokos;

    public TokoAdapter(Context context, List<TokoResult> tokos) {
        this.context = context;
        this.tokos = tokos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.row_toko, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtTokoName.setText(tokos.get(position).getNama());
        holder.txtTokoPlace.setText(tokos.get(position).getAlamat() + " (" + tokos.get(position).getIdKecamatan() + ")");
        Glide.with(context).load(tokos.get(position).getFoto()).into(holder.imgToko);
        holder.imgToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemTokoActivity.class);
                intent.putExtra("toko",tokos.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (tokos != null) ? tokos.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTokoName, txtTokoPlace;
        private ImageView imgToko;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTokoName = (TextView) itemView.findViewById(R.id.txt_toko_name);
            txtTokoPlace = (TextView) itemView.findViewById(R.id.txt_toko_location);
            imgToko = (ImageView) itemView.findViewById(R.id.img_toko);
        }
    }
}
