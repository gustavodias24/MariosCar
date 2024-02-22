package benicio.soluces.marioscar.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import benicio.soluces.marioscar.R;
import benicio.soluces.marioscar.model.ItemModel;
import benicio.soluces.marioscar.utils.MathUtils;

public class AdapterItens extends RecyclerView.Adapter<AdapterItens.MyViewHolder>{
    List<ItemModel> itens;
    Context c;

    TextInputLayout fieldValue;

    public AdapterItens(List<ItemModel> itens, Context c, TextInputLayout fieldValue) {
        this.itens = itens;
        this.c = c;
        this.fieldValue = fieldValue;
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

        holder.excluir.setOnClickListener( view -> {
            itens.remove(position);
            Double valorAntigo = MathUtils.converterParaDouble(fieldValue.getEditText().getText().toString());
            Double valorRemovido = MathUtils.converterParaDouble(item.getValorPecaMultipl());
            String valorAtualizado = MathUtils.formatarMoeda((valorAntigo - valorRemovido));
            fieldValue.getEditText().setText(valorAtualizado);
            Toast.makeText(c, "Item removido", Toast.LENGTH_SHORT).show();
            this.notifyDataSetChanged();

        });

        holder.itemView.getRootView().setClickable(false);

        holder.editar.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView info;
        Button editar, excluir;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.infos_text);
            editar = itemView.findViewById(R.id.editar_btn_veiculo_cliente);
            excluir = itemView.findViewById(R.id.excluir_btn_veiculo_cliente);
        }
    }
}
