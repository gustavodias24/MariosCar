package benicio.soluces.marioscar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import benicio.soluces.marioscar.adapters.AdapterCliente;
import benicio.soluces.marioscar.adapters.AdapterOS;
import benicio.soluces.marioscar.databinding.ActivityClienteBinding;
import benicio.soluces.marioscar.databinding.ActivityExibicaoBinding;
import benicio.soluces.marioscar.model.OSModel;
import benicio.soluces.marioscar.model.UsuarioModel;

public class ExibicaoActivity extends AppCompatActivity {

    private ActivityExibicaoBinding mainBinding;
    private DatabaseReference refOs = FirebaseDatabase.getInstance().getReference().getRef().child("os");
    private DatabaseReference refClientes = FirebaseDatabase.getInstance().getReference().getRef().child("clientes");
    private Bundle b;
    private RecyclerView r;
    AdapterOS adapterOS;
    AdapterCliente adapterCliente;
    List<OSModel> listaOs = new ArrayList<>();
    List<UsuarioModel> listaClientes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityExibicaoBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        b = getIntent().getExtras();
        String title = "";

        switch (Objects.requireNonNull(b).getInt("t", 0)){
            case 666: //cliente
                title = "Lista de clientes";
                configurarListenerCliente();
                break;
            case 1:
                title = "Lista de os";
                configurarListenerOS();
                break;
        }
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        configurarRecyclerExibicao();
    }

    private void configurarListenerCliente() {
        refClientes.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    listaClientes.clear();
                    for ( DataSnapshot dado : snapshot.getChildren()){
                        UsuarioModel u = dado.getValue(UsuarioModel.class);
                        listaClientes.add(u);
                    }
                    adapterCliente.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configurarRecyclerExibicao() {
        r = mainBinding.recyclerExibicao;
        r.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        r.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        r.setHasFixedSize(true);

        if ( b.getInt("t") == 1){
            adapterOS = new AdapterOS(listaOs, this, getApplicationContext());
            r.setAdapter(adapterOS);
        }else{
            adapterCliente = new AdapterCliente(listaClientes, getApplicationContext(), 666);
            r.setAdapter(adapterCliente);
        }

    }

    private void configurarListenerOS(){
        refOs.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ( snapshot.exists() ){
                    listaOs.clear();
                    for( DataSnapshot dado : snapshot.getChildren()){
                        OSModel osModel = dado.getValue(OSModel.class);
                        listaOs.add(osModel);
                    }
                    adapterOS.notifyDataSetChanged();
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