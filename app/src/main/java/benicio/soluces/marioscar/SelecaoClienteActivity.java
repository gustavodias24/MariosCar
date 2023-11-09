package benicio.soluces.marioscar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import benicio.soluces.marioscar.adapters.AdapterCliente;
import benicio.soluces.marioscar.databinding.ActivityClienteBinding;
import benicio.soluces.marioscar.databinding.ActivitySelecaoClienteBinding;
import benicio.soluces.marioscar.model.UsuarioModel;

public class SelecaoClienteActivity extends AppCompatActivity {

    private ActivitySelecaoClienteBinding mainBinding ;
    private DatabaseReference refClientes = FirebaseDatabase.getInstance().getReference().getRef().child("clientes");

    AdapterCliente adapterCliente;
    List<UsuarioModel> clientes = new ArrayList<>();
    private RecyclerView r;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivitySelecaoClienteBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bundle = getIntent().getExtras();

        getSupportActionBar().setTitle("Selecionar cliente");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        configurarRecyclerView();
        configurarListener();

    }

    private void configurarRecyclerView() {
        r = mainBinding.recyclerClientes;
        r.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        r.setHasFixedSize(true);
        r.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        adapterCliente = new AdapterCliente(clientes, getApplicationContext(), bundle.getInt("t"));
        r.setAdapter(adapterCliente);

    }

    private void configurarListener(){
        refClientes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ( snapshot.exists() ){
                    clientes.clear();
                    for ( DataSnapshot dado : snapshot.getChildren()){
                        clientes.add(dado.getValue(UsuarioModel.class));
                    }

                    adapterCliente.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if ( item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}