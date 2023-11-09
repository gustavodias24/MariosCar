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

import benicio.soluces.marioscar.ExibidorDeOSActivity;
import benicio.soluces.marioscar.OSActivity;
import benicio.soluces.marioscar.R;
import benicio.soluces.marioscar.model.VeiculoModel;

public class AdapterVeiculo extends RecyclerView.Adapter<AdapterVeiculo.MyViewHolder>{
    List<VeiculoModel> veiculos;
    Context c;

    int t;

    public AdapterVeiculo(List<VeiculoModel> veiculos, Context c, int t) {
        this.veiculos = veiculos;
        this.c = c;
        this.t = t;
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
            Intent i = null;
            switch (t){
                case 555:
                    i = new Intent(c, OSActivity.class);
                    break;
                case 666:
                    i =  new Intent(c, ExibidorDeOSActivity.class);
                    break;
            }

            assert i != null;
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
