package benicio.soluces.marioscar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import benicio.soluces.marioscar.adapters.AdapterCliente;
import benicio.soluces.marioscar.adapters.AdapterOS;
import benicio.soluces.marioscar.databinding.ActivityExibidorDeOsactivityBinding;
import benicio.soluces.marioscar.databinding.ActivitySelecaoVeiculoClienteBinding;
import benicio.soluces.marioscar.model.OSModel;

public class ExibidorDeOSActivity extends AppCompatActivity {

    private DatabaseReference refOs = FirebaseDatabase.getInstance().getReference().getRef().child("os");
    private ActivityExibidorDeOsactivityBinding mainBinding;
    private Bundle bundle;
    List<OSModel> listaOs = new ArrayList<>();
    private RecyclerView r;
    AdapterOS adapterOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityExibidorDeOsactivityBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bundle = getIntent().getExtras();

        getSupportActionBar().setTitle("Ordens de serviço");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        configurarRecyclerExibicao();
        configurarListenerOS();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if ( item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void configurarRecyclerExibicao() {
        r = mainBinding.recyclerExibicao;
        r.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        r.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        r.setHasFixedSize(true);

        adapterOS = new AdapterOS(listaOs, this, getApplicationContext());
        r.setAdapter(adapterOS);

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
                        assert osModel != null;
                        if ( osModel.getIdCarro().equals(bundle.getString("idCarro"))){
                            listaOs.add(osModel);
                        }
                    }
                    adapterOS.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}