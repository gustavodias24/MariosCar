package benicio.soluces.marioscar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import benicio.soluces.marioscar.OSActivity;
import benicio.soluces.marioscar.R;
import benicio.soluces.marioscar.model.VeiculoModel;

public class AdapterVeiculo extends RecyclerView.Adapter<AdapterVeiculo.MyViewHolder>{
    List<VeiculoModel> veiculos;
    Context c;

    public AdapterVeiculo(List<VeiculoModel> veiculos, Context c) {
        this.veiculos = veiculos;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.exibir_clientes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VeiculoModel veiculoModel = veiculos.get(position);
        holder.infos.setText(
                veiculoModel.toString()
        );

        holder.itemView.getRootView().setOnClickListener( view -> {
            Intent i = new Intent(c, OSActivity.class);
            i.putExtra("idCarro", veiculoModel.getId());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return veiculos.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView infos;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            infos = itemView.findViewById(R.id.infos_text);
        }
    }
}
