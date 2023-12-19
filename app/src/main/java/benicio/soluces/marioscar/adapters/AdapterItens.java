package benicio.soluces.marioscar.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import benicio.soluces.marioscar.R;
import benicio.soluces.marioscar.model.ItemModel;
import benicio.soluces.marioscar.utils.MathUtils;

public class AdapterItens extends RecyclerView.Adapter<AdapterItens.MyViewHolder>{
    List<ItemModel> itens;
    Context c;

    EditText editCalculo;

    Boolean isServico;
    public AdapterItens(List<ItemModel> itens, Context c, EditText editCalculo,Boolean isServico) {
        this.itens = itens;
        this.c = c;
        this.editCalculo = editCalculo;
        this.isServico = isServico;
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

        holder.editar.setVisibility(View.GONE);

        holder.info.setText(item.toString());

        holder.itemView.getRootView().setClickable(false);

        holder.excluir.setOnClickListener(view -> {
            itens.remove(position);
            Toast.makeText(c, "Item removido", Toast.LENGTH_SHORT).show();
            if (!isServico){
                calcularValor(
                        editCalculo,
                        item.getValorPecaMultipl()
                );
            }else{
                calcularValor(
                        editCalculo,
                        item.getValor()
                );
            }

            this.notifyDataSetChanged();
        });
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

    @SuppressLint("SetTextI18n")
    public  void calcularValor(EditText editValorExistente, float novoValor){
        String valorAtual = editValorExistente.getText().toString().trim().replace("R$", "").replace(" ", "");

        if ( valorAtual.isEmpty() ){
            editValorExistente.setText(novoValor + "");
        }else{
            float somaTotal = Float.parseFloat(valorAtual) - novoValor;
            editValorExistente.setText(
                    somaTotal + ""
            );
        }
    }
}
