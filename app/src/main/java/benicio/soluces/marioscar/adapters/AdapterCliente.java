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

import benicio.soluces.marioscar.EditarClienteActivity;
import benicio.soluces.marioscar.OSActivity;
import benicio.soluces.marioscar.R;
import benicio.soluces.marioscar.SelecaoVeiculoClienteActivity;
import benicio.soluces.marioscar.VeiculoActivity;
import benicio.soluces.marioscar.model.UsuarioModel;
import benicio.soluces.marioscar.utils.DatabaseUtils;

public class AdapterCliente extends RecyclerView.Adapter<AdapterCliente.MyViewHolder> {
    private DatabaseReference refClientes = FirebaseDatabase.getInstance().getReference().getRef().child(DatabaseUtils.CLIENTES_DB);
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

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UsuarioModel usuarioModel = clientes.get(position);
        holder.infos.setText(String.format(
                "Nome: %s\nNome fantasia: %s", usuarioModel.getNome(), usuarioModel.getNome2()
        ));

        holder.itemView.getRootView().setOnClickListener( view -> {
            Intent i = null;
            switch (t){
                case 444:
                    i = new Intent(c, VeiculoActivity.class);
                    i.putExtra("t", 444);

                    break;
                case 555:
                    i = new Intent(c, SelecaoVeiculoClienteActivity.class);
                    i.putExtra("t", 555);

                    break;
                case 666:
                    i = new Intent(c, SelecaoVeiculoClienteActivity.class);
                    i.putExtra("t", 666);
                    i.putExtra("exibirTodosOsDadosCliente", true);
                    break;
            }

            assert i != null;
            i.putExtra("idCliente", usuarioModel.getId());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(i);

        });

        holder.btnExcluir.setOnClickListener( view -> {
            refClientes.child(usuarioModel.getId()).setValue(null).addOnCompleteListener( taks -> {
                if ( taks.isSuccessful() ){
                    Toast.makeText(c, "Cliente removido!", Toast.LENGTH_SHORT).show();
                    clientes.remove(position);
                    this.notifyDataSetChanged();
                }
            });
        });

        holder.btnEditar.setOnClickListener( view -> {
            Intent i = new Intent(c, EditarClienteActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("id", usuarioModel.getId());
            c.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
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
