package benicio.soluces.marioscar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Base64;
import java.util.UUID;

import benicio.soluces.marioscar.databinding.ActivityClienteBinding;
import benicio.soluces.marioscar.databinding.ActivityMainBinding;
import benicio.soluces.marioscar.model.UsuarioModel;

public class ClienteActivity extends AppCompatActivity {

    private ActivityClienteBinding mainBinding;
    private DatabaseReference refClientes = FirebaseDatabase.getInstance().getReference().getRef().child("clientes");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityClienteBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        getSupportActionBar().setTitle("Cadastro de cliente");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mainBinding.cadastrar.setOnClickListener( view -> {
            String id = Base64.getEncoder().encodeToString(
                        UUID.randomUUID().toString().getBytes());

            String nome, nome2, rua, bairro, cidade, uf, cep,
                    telefone, whatsapp, email, docuemnto, data;

            nome = mainBinding.nomeField.getEditText().getText().toString();
            nome2 = mainBinding.nomeFantasiaField.getEditText().getText().toString();
            rua = mainBinding.ruaField.getEditText().getText().toString();
            bairro = mainBinding.bairroField.getEditText().getText().toString();
            cidade = mainBinding.cidadeField.getEditText().getText().toString();
            uf = mainBinding.ufField.getEditText().getText().toString();
            cep = mainBinding.cepField.getEditText().getText().toString();
            telefone = mainBinding.telefoneField.getEditText().getText().toString();
            whatsapp = mainBinding.whatsappField.getEditText().getText().toString();
            email = mainBinding.emailField.getEditText().getText().toString();
            docuemnto = mainBinding.documentoField.getEditText().getText().toString();
            data = mainBinding.dataNascimentoField.getEditText().getText().toString();
            Toast.makeText(this, "Aguarde...", Toast.LENGTH_SHORT).show();
            refClientes.child(id).setValue(
                    new UsuarioModel(id, nome, nome2, rua, bairro,cidade, uf, cep , telefone, whatsapp,
                            email, docuemnto, data)
            ).addOnCompleteListener(task -> {
                if ( task.isSuccessful() ){
                    Toast.makeText(this, "Cadastrado realizado!", Toast.LENGTH_LONG).show();
                    mainBinding.nomeField.getEditText().setText("");
                    mainBinding.nomeFantasiaField.getEditText().setText("");
                    mainBinding.ruaField.getEditText().setText("");
                    mainBinding.bairroField.getEditText().setText("");
                    mainBinding.cidadeField.getEditText().setText("");
                    mainBinding.ufField.getEditText().setText("");
                    mainBinding.cepField.getEditText().setText("");
                    mainBinding.telefoneField.getEditText().setText("");
                    mainBinding.whatsappField.getEditText().setText("");
                    mainBinding.emailField.getEditText().setText("");
                    mainBinding.documentoField.getEditText().setText("");
                    mainBinding.dataNascimentoField.getEditText().setText("");
                }else{
                    Toast.makeText(this, "Problema de conexão, tente novamente.", Toast.LENGTH_LONG).show();
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