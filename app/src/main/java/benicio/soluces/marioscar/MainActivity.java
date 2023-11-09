package benicio.soluces.marioscar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import benicio.soluces.marioscar.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainBinding.cliente.setOnClickListener( view -> {
            startActivity(new Intent(getApplicationContext(), ClienteActivity.class));
        });

        mainBinding.veiculo.setOnClickListener( view -> {
            Intent i = new Intent(getApplicationContext(), SelecaoClienteActivity.class);
            i.putExtra("t", 444);
            startActivity(i);
        });

        mainBinding.os.setOnClickListener( view -> {
            Intent i = new Intent(getApplicationContext(), SelecaoClienteActivity.class);
            i.putExtra("t", 555);
            startActivity(i);
        });

        mainBinding.exibirOs.setOnClickListener( view -> {
            Intent i = new Intent(getApplicationContext(), ExibicaoActivity.class);
            i.putExtra("t", 1);
            startActivity(i);
        });

        mainBinding.exibirClientes.setOnClickListener( view -> {
            Intent i = new Intent(getApplicationContext(), ExibicaoActivity.class);
            i.putExtra("t", 666);
            startActivity(i);
        });
    }
}