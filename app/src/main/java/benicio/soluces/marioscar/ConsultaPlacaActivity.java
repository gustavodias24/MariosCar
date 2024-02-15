package benicio.soluces.marioscar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import benicio.soluces.marioscar.adapters.AdapterOS;
import benicio.soluces.marioscar.databinding.ActivityConsultaPlacaBinding;
import benicio.soluces.marioscar.databinding.ActivityMainBinding;
import benicio.soluces.marioscar.model.OSModel;
import benicio.soluces.marioscar.utils.DatabaseUtils;

public class ConsultaPlacaActivity extends AppCompatActivity {

    private DatabaseReference refOs = FirebaseDatabase.getInstance().getReference().getRef().child(DatabaseUtils.OS_DB);
    private ActivityConsultaPlacaBinding mainBinding;
    private List<OSModel> oss = new ArrayList<>();
    private RecyclerView r;
    AdapterOS adapterOS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityConsultaPlacaBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainBinding.consultar.setOnClickListener( view -> {
            if ( mainBinding.documentoField.getText().toString().isEmpty()) {
                Toast.makeText(this, "Escreva Algo!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Consultando...", Toast.LENGTH_SHORT).show();
                consultar(
                        mainBinding.documentoField.getText().toString()
                );
            }

        });

        configurarRecyclerExibicao();
    }

    private void consultar(String placa) {
        refOs.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ( snapshot.exists() ){
                    oss.clear();
                    for ( DataSnapshot dado : snapshot.getChildren()){
                        OSModel osModel = dado.getValue(OSModel.class);

                        if ( osModel != null && osModel.getUsuarioModel() != null){
                            if (osModel.getUsuarioModel().getDocumento().toLowerCase().trim().equals(placa.toLowerCase().trim())){
                                oss.add(osModel);
                            }
                        }

                    }
                    adapterOS.notifyDataSetChanged();
                    if ( oss.isEmpty() ){
                        mainBinding.textInfo.setText("Nenhuma OS encontrada!");
                    }else{
                        mainBinding.textInfo.setText("Todas as OS do veículo:");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void configurarRecyclerExibicao() {
        r = mainBinding.recylcerOs;
        r.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        r.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        r.setHasFixedSize(true);

        adapterOS = new AdapterOS(oss, this, getApplicationContext());
        r.setAdapter(adapterOS);

    }
}