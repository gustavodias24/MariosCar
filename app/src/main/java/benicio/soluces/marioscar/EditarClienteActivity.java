package benicio.soluces.marioscar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import benicio.soluces.marioscar.databinding.ActivityEditarClienteBinding;
import benicio.soluces.marioscar.model.UsuarioModel;

public class EditarClienteActivity extends AppCompatActivity {

    private ActivityEditarClienteBinding mainBinding;
    private DatabaseReference refClientes = FirebaseDatabase.getInstance().getReference().getRef().child("clientes");
    private Bundle b;

    private UsuarioModel usuarioModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityEditarClienteBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        b = getIntent().getExtras();


        refClientes.child(b.getString("id", "")).get().addOnCompleteListener( taks -> {
            if ( taks.isSuccessful() ){
                usuarioModel = taks.getResult().getValue(UsuarioModel.class);

                getSupportActionBar().setTitle("Editar "+ usuarioModel.getNome());

                mainBinding.nomeField.getEditText().setText(usuarioModel.getNome());
                mainBinding.nomeFantasiaField.getEditText().setText(usuarioModel.getNome2());
                mainBinding.ruaField.getEditText().setText(usuarioModel.getRua());
                mainBinding.bairroField.getEditText().setText(usuarioModel.getBairro());
                mainBinding.cidadeField.getEditText().setText(usuarioModel.getCidade());
                mainBinding.ufField.getEditText().setText(usuarioModel.getUf());
                mainBinding.cepField.getEditText().setText(usuarioModel.getCep());
                mainBinding.telefoneField.getEditText().setText(usuarioModel.getTelefone());
                mainBinding.whatsappField.getEditText().setText(usuarioModel.getWhatsapp());
                mainBinding.emailField.getEditText().setText(usuarioModel.getEmail());
                mainBinding.documentoField.getEditText().setText(usuarioModel.getDocumento());
                mainBinding.dataNascimentoField.getEditText().setText(usuarioModel.getData());

            }else{
                Toast.makeText(this, "Erro de conexão!", Toast.LENGTH_SHORT).show();
            }
        });

        mainBinding.editar.setOnClickListener( view -> {

            usuarioModel.setNome(mainBinding.nomeField.getEditText().getText().toString());
            usuarioModel.setNome2(mainBinding.nomeFantasiaField.getEditText().getText().toString());
            usuarioModel.setRua(mainBinding.ruaField.getEditText().getText().toString());
            usuarioModel.setBairro(mainBinding.bairroField.getEditText().getText().toString());
            usuarioModel.setCidade(mainBinding.cidadeField.getEditText().getText().toString());
            usuarioModel.setUf(mainBinding.ufField.getEditText().getText().toString());
            usuarioModel.setCep(mainBinding.cepField.getEditText().getText().toString());
            usuarioModel.setTelefone(mainBinding.telefoneField.getEditText().getText().toString());
            usuarioModel.setWhatsapp(mainBinding.whatsappField.getEditText().getText().toString());
            usuarioModel.setEmail(mainBinding.emailField.getEditText().getText().toString());
            usuarioModel.setDocumento(mainBinding.documentoField.getEditText().getText().toString());
            usuarioModel.setData(mainBinding.dataNascimentoField.getEditText().getText().toString());

            refClientes.child(b.getString("id", "")).setValue(usuarioModel).addOnCompleteListener(task -> {
                if ( task.isSuccessful()){
                    Toast.makeText(this, "Editado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
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