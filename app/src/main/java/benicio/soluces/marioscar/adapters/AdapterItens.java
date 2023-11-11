package benicio.soluces.marioscar.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import benicio.soluces.marioscar.R;
import benicio.soluces.marioscar.model.ItemModel;

public class AdapterItens extends RecyclerView.Adapter<AdapterItens.MyViewHolder>{
    List<ItemModel> itens;
    Context c;

    public AdapterItens(List<ItemModel> itens, Context c) {
        this.itens = itens;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.exibir_clientes, parent, false));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemModel item = itens.get(position);

        holder.info.setText(item.toString());

        holder.itemView.getRootView().setOnLongClickListener( view -> {
            itens.remove(position);
            Toast.makeText(c, "Item removido", Toast.LENGTH_SHORT).show();
            this.notifyDataSetChanged();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView info;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.infos_text);
        }
    }
}
