package benicio.soluces.marioscar.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import benicio.soluces.marioscar.EditarVeiculoActivity;
import benicio.soluces.marioscar.ExibidorDeOSActivity;
import benicio.soluces.marioscar.OSActivity;
import benicio.soluces.marioscar.R;
import benicio.soluces.marioscar.model.VeiculoModel;
import benicio.soluces.marioscar.utils.DatabaseUtils;

public class AdapterVeiculo extends RecyclerView.Adapter<AdapterVeiculo.MyViewHolder>{

    private DatabaseReference refVeiculos = FirebaseDatabase.getInstance().getReference().getRef().child(DatabaseUtils.VEICULOS_DB);
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

    @SuppressLint("NotifyDataSetChanged")
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
            i.putExtra("idCliente", veiculoModel.getIdCliente());
            i.putExtra("idCarro", veiculoModel.getId());
            i.putExtra("placaCarro", veiculoModel.getPlaca());

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(i);


        });

        holder.btnEditar.setOnClickListener( view -> {
            Intent i = new Intent(c, EditarVeiculoActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("id", veiculoModel.getId());
            c.startActivity(i);
        });

        holder.btnExcluir.setOnClickListener( view -> {
            refVeiculos.child(veiculoModel.getId()).setValue(null).addOnCompleteListener(task -> {
                if ( task.isSuccessful()){
//                    veiculos.remove(position);
                    Toast.makeText(c, "Veículo removido", Toast.LENGTH_SHORT).show();
                    this.notifyDataSetChanged();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return veiculos.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView infos;
        Button btnEditar, btnExcluir;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            infos = itemView.findViewById(R.id.infos_text);
            btnEditar = itemView.findViewById(R.id.editar_btn_veiculo_cliente);
            btnExcluir = itemView.findViewById(R.id.excluir_btn_veiculo_cliente);
        }
    }
}
