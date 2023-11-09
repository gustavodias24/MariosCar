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

import benicio.soluces.marioscar.R;
import benicio.soluces.marioscar.SelecaoVeiculoClienteActivity;
import benicio.soluces.marioscar.VeiculoActivity;
import benicio.soluces.marioscar.model.UsuarioModel;

public class AdapterCliente extends RecyclerView.Adapter<AdapterCliente.MyViewHolder> {
    List<UsuarioModel> clientes;
    Context c;
    int t;

    public AdapterCliente(List<UsuarioModel> clientes, Context c, int t) {
        this.clientes = clientes;
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
        UsuarioModel usuarioModel = clientes.get(position);
        holder.infos.setText(String.format(
                "Nome: %s\nNome fantasia: %s", usuarioModel.getNome(), usuarioModel.getNome2()
        ));

        holder.itemView.getRootView().setOnClickListener( view -> {
            Intent i = null;
            switch (t){
                case 0:
                    i = new Intent(c, VeiculoActivity.class);

                    break;
                case 1:
                    i = new Intent(c, SelecaoVeiculoClienteActivity.class);
                    break;
            }

            assert i != null;
            i.putExtra("idCliente", usuarioModel.getId());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(i);

        });
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView infos;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            infos = itemView.findViewById(R.id.infos_text);
        }
    }

}
