package benicio.soluces.marioscar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import benicio.soluces.marioscar.adapters.AdapterVeiculo;
import benicio.soluces.marioscar.databinding.ActivitySelecaoVeiculoClienteBinding;
import benicio.soluces.marioscar.model.UsuarioModel;
import benicio.soluces.marioscar.model.VeiculoModel;

public class SelecaoVeiculoClienteActivity extends AppCompatActivity {

    private ActivitySelecaoVeiculoClienteBinding mainBinding;
    private Bundle bundle;

    private RecyclerView r;
    AdapterVeiculo adapterVeiculo;
    private List<VeiculoModel> veiculos = new ArrayList<>();
    private DatabaseReference refVeiculos = FirebaseDatabase.getInstance().getReference().getRef().child("veiculos");
    private DatabaseReference refClientes = FirebaseDatabase.getInstance().getReference().getRef().child("clientes");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivitySelecaoVeiculoClienteBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bundle = getIntent().getExtras();

        getSupportActionBar().setTitle("Selecionar veículo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        configurarRecyclerView();
        configurarListener("");

        if ( bundle.getBoolean("exibirTodosOsDadosCliente", false)){
            mainBinding.layoutDadosCliente.setVisibility(View.VISIBLE);

            refClientes.child(bundle.getString("idCliente",  "")).get().addOnCompleteListener(task -> {
                String infosUser = "Falha ao buscar dados do usuário";

                if ( task.isSuccessful() ){
                    UsuarioModel usuarioModel = task.getResult().getValue(UsuarioModel.class);
                    infosUser = usuarioModel.toString();
                }

                mainBinding.dadosCliente.setText(infosUser);
            });
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pesquisa, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                configurarListener(newText.toLowerCase().trim());
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    private void configurarListener(String query) {
        refVeiculos.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ( snapshot.exists() ){
                    veiculos.clear();
                    for ( DataSnapshot dado : snapshot.getChildren()){
                        VeiculoModel veiculoModel = dado.getValue(VeiculoModel.class);
                        if (veiculoModel.getIdCliente().equals(bundle.getString("idCliente", "")) ){
                            if ( query.isEmpty() ){
                                veiculos.add(veiculoModel);
                            }else{
                                if(
                                        veiculoModel.getPlaca().toLowerCase().trim().contains(query) ||
                                        veiculoModel.getChassi().toLowerCase().trim().contains(query) ||
                                        veiculoModel.getAnoFab().toLowerCase().trim().contains(query) ||
                                        veiculoModel.getRenavam().toLowerCase().trim().contains(query) ||
                                        veiculoModel.getMarca().toLowerCase().trim().contains(query) ||
                                        veiculoModel.getModelo().toLowerCase().trim().contains(query)
                                ){
                                    veiculos.add(veiculoModel);
                                }
                            }
                        }
                    }
                    adapterVeiculo.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configurarRecyclerView() {
        r = mainBinding.recyclerVeiculos;
        r.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        r.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        r.setHasFixedSize(true);
        adapterVeiculo = new AdapterVeiculo(veiculos, getApplicationContext(), bundle.getInt("t", 0));
        r.setAdapter(adapterVeiculo);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if ( item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}