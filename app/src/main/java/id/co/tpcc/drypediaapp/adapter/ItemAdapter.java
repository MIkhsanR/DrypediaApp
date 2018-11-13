package id.co.tpcc.drypediaapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import id.co.tpcc.drypediaapp.DetailTokoActivity;
import id.co.tpcc.drypediaapp.ItemTokoActivity;
import id.co.tpcc.drypediaapp.R;
import id.co.tpcc.drypediaapp.model.ItemResult;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    Context context;
    List<ItemResult> items;

    public ItemAdapter(Context context, List<ItemResult> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtItemName.setText(items.get(position).getNama());
        holder.txtItemJenis.setText(items.get(position).getNamajenis() + " (" + items.get(position).getIdJenis() + ")");
        holder.txtItemHarga.setText(items.get(position).getHarga());
        holder.btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Pesan");
                // Get custom login form view.
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogLayout = inflater.inflate(R.layout.dialog_pesan, null);
                builder.setView(dialogLayout);
                builder.setCancelable(true);
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (items != null) ? items.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtItemName, txtItemJenis, txtItemHarga;
        private Button btnPesan;
        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtItemName = (TextView) itemView.findViewById(R.id.txt_item_name);
            txtItemJenis = (TextView) itemView.findViewById(R.id.txt_item_jenis);
            txtItemHarga = (TextView) itemView.findViewById(R.id.txt_item_harga);
            btnPesan = (Button) itemView.findViewById(R.id.btn_konfirmasi);
            this.itemView = itemView;
        }
    }
}
