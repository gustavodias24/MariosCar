package benicio.soluces.marioscar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Base64;
import java.util.UUID;

import benicio.soluces.marioscar.databinding.ActivitySelecaoClienteBinding;
import benicio.soluces.marioscar.databinding.ActivityVeiculoBinding;
import benicio.soluces.marioscar.model.VeiculoModel;
import benicio.soluces.marioscar.utils.DatabaseUtils;

public class VeiculoActivity extends AppCompatActivity {

    private DatabaseReference refVeiculos = FirebaseDatabase.getInstance().getReference().getRef().child(DatabaseUtils.VEICULOS_DB);
    private ActivityVeiculoBinding mainBinding;
    private Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityVeiculoBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        getSupportActionBar().setTitle("Cadastrar veículo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        b = getIntent().getExtras();

        mainBinding.cadastrar.setOnClickListener( view -> {
            String id = Base64.getEncoder().encodeToString(
                    UUID.randomUUID().toString().getBytes()
            );

            String idCliente = b.getString("idCliente");

            String placa, anoFab, anoMode, kmAtual, combustivel,nomeCliente,
                    marca,modelo,cor,ranavam,chassi;

            placa = mainBinding.placaField.getEditText().getText().toString();
            anoFab = mainBinding.anoFabField.getEditText().getText().toString();
            anoMode = mainBinding.anoModField.getEditText().getText().toString();
            kmAtual = mainBinding.kmField.getEditText().getText().toString();
            combustivel = mainBinding.combustivelField.getEditText().getText().toString();
            nomeCliente = mainBinding.nomeClienteField.getEditText().getText().toString();
            marca = mainBinding.marcaField.getEditText().getText().toString();
            modelo = mainBinding.modeloField.getEditText().getText().toString();
            cor = mainBinding.corField.getEditText().getText().toString();
            ranavam = mainBinding.renavamField.getEditText().getText().toString();
            chassi = mainBinding.chassiField.getEditText().getText().toString();

            Toast.makeText(this, "Aguarde.", Toast.LENGTH_SHORT).show();

            refVeiculos.child(id).setValue(
                    new VeiculoModel(id, idCliente, placa, anoFab, anoMode, kmAtual, combustivel, nomeCliente, marca,modelo, cor, ranavam, chassi)
            ).addOnCompleteListener( task -> {
                if ( task.isSuccessful() ){
                    Toast.makeText(this, "Cadastrado com sucesso.", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else{
                    Toast.makeText(this, "Problema de conexão, tente novamente.", Toast.LENGTH_SHORT).show();
                }
            });
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