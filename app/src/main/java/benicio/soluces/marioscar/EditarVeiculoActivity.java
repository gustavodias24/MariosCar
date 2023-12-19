package benicio.soluces.marioscar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import benicio.soluces.marioscar.databinding.ActivityEditarVeiculoBinding;
import benicio.soluces.marioscar.model.VeiculoModel;

public class EditarVeiculoActivity extends AppCompatActivity {

    private DatabaseReference refVeiculos = FirebaseDatabase.getInstance().getReference().getRef().child("veiculos");
    ActivityEditarVeiculoBinding mainBinding;
    private Bundle b;

    VeiculoModel veiculoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityEditarVeiculoBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        b = getIntent().getExtras();

        refVeiculos.child(b.getString("id", "")).get().addOnCompleteListener(task -> {
            if ( task.isSuccessful() ){
                veiculoModel = task.getResult().getValue(VeiculoModel.class);

                getSupportActionBar().setTitle("Editar " + veiculoModel.getPlaca());

                mainBinding.placaField.getEditText().setText(veiculoModel.getPlaca());
                mainBinding.anoFabField.getEditText().setText(veiculoModel.getAnoFab());
                mainBinding.anoModField.getEditText().setText(veiculoModel.getAnoMode());
                mainBinding.kmField.getEditText().setText(veiculoModel.getKmAtual());
                mainBinding.combustivelField.getEditText().setText(veiculoModel.getCombustivel());
                mainBinding.nomeClienteField.getEditText().setText(veiculoModel.getNomeCliente());
                mainBinding.marcaField.getEditText().setText(veiculoModel.getMarca());
                mainBinding.modeloField.getEditText().setText(veiculoModel.getModelo());
                mainBinding.corField.getEditText().setText(veiculoModel.getCor());
                mainBinding.renavamField.getEditText().setText(veiculoModel.getRenavam());
                mainBinding.chassiField.getEditText().setText(veiculoModel.getChassi());



            }else{
                Toast.makeText(this, "Problema de conexão!", Toast.LENGTH_SHORT).show();
            }
        });

        mainBinding.editar.setOnClickListener( view -> {

            veiculoModel.setPlaca(mainBinding.placaField.getEditText().getText().toString());
            veiculoModel.setAnoFab(mainBinding.anoFabField.getEditText().getText().toString());
            veiculoModel.setAnoMode(mainBinding.anoModField.getEditText().getText().toString());
            veiculoModel.setKmAtual(mainBinding.kmField.getEditText().getText().toString());
            veiculoModel.setCombustivel( mainBinding.combustivelField.getEditText().getText().toString());
            veiculoModel.setNomeCliente(mainBinding.nomeClienteField.getEditText().getText().toString());
            veiculoModel.setMarca(mainBinding.marcaField.getEditText().getText().toString());
            veiculoModel.setModelo(mainBinding.modeloField.getEditText().getText().toString());
            veiculoModel.setCor(mainBinding.corField.getEditText().getText().toString());
            veiculoModel.setRenavam(mainBinding.renavamField.getEditText().getText().toString());
            veiculoModel.setChassi(mainBinding.chassiField.getEditText().getText().toString());

            refVeiculos.child(b.getString("id", "")).setValue(veiculoModel).addOnCompleteListener( taks -> {
                if ( taks.isSuccessful() ){
                    Toast.makeText(this, "Veículo atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(this, "Problema de conexão!", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if ( item.getItemId() == android.R.id.home){finish();}
        return super.onOptionsItemSelected(item);
    }
}