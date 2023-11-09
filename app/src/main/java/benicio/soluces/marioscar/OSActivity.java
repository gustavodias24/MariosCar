package benicio.soluces.marioscar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import benicio.soluces.marioscar.adapters.AdapterImages;
import benicio.soluces.marioscar.databinding.ActivityOsactivityBinding;
import benicio.soluces.marioscar.databinding.LoadingLayoutBinding;
import benicio.soluces.marioscar.model.OSModel;
import benicio.soluces.marioscar.model.ResponseIngurModel;
import benicio.soluces.marioscar.utils.ImageUtils;
import benicio.soluces.marioscar.utils.RetrofitUtils;
import benicio.soluces.marioscar.services.ServiceIngur;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OSActivity extends AppCompatActivity {
    Uri imageUri;
    private DatabaseReference refOs = FirebaseDatabase.getInstance().getReference().getRef().child("os");
    private Dialog dialogCarregando;
    private static final int PERMISSON_CODE = 1000;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TOKEN = "282c6d7932402b7665da78ee7c51311556ce6c8a";
    private ActivityOsactivityBinding mainBinding;
    Retrofit retrofitIngur = RetrofitUtils.createRetrofitIngur();
    ServiceIngur serviceIngur = RetrofitUtils.createServiceIngur(retrofitIngur);

    AdapterImages adapterImages;
    RecyclerView r;
    List<String> imagesLink = new ArrayList<>();

    private Bundle b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityOsactivityBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        b = getIntent().getExtras();

        getSupportActionBar().setTitle("Criar Ordem de serviço");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        configurarDialogCarregando();

        mainBinding.cadastrar.setOnClickListener(view -> {
            dialogCarregando.show();
            Toast.makeText(this, "Aguarde!", Toast.LENGTH_SHORT).show();

            String id = Base64.getEncoder().encodeToString(
                    UUID.randomUUID().toString().getBytes()
            );

            String idCarro = b.getString("idCarro", "");

            String descricao, descricaoPeca, valorTotal, valorService, desconto, total, obs;

            descricao = mainBinding.descricaoField.getEditText().getText().toString();
            descricaoPeca = mainBinding.descricaoPeAField.getEditText().getText().toString();
            valorTotal = mainBinding.valorTotalField.getEditText().getText().toString();
            valorService = mainBinding.valorServicoField.getEditText().getText().toString();
            desconto = mainBinding.descontoField.getEditText().getText().toString();
            total = mainBinding.totalField.getEditText().getText().toString();
            obs = mainBinding.obsField.getEditText().getText().toString();

            refOs.child(id).setValue(
                    new OSModel(
                            id,
                            idCarro,
                            descricao, descricaoPeca,
                            valorTotal, valorService, desconto, total, obs,
                            imagesLink, mainBinding.bateria.isChecked(),
                            mainBinding.alarme.isChecked(),
                            mainBinding.buzina.isChecked(),
                            mainBinding.trava.isChecked(),
                            mainBinding.vidro.isChecked(),
                            mainBinding.tapete.isChecked(),
                            mainBinding.chaveRoda.isChecked(),
                            mainBinding.macaco.isChecked(),
                            mainBinding.triangulo.isChecked(),
                            mainBinding.extintor.isChecked(),
                            mainBinding.som.isChecked()
                    )
            ).addOnCompleteListener(task -> {
                if ( task.isSuccessful() ){
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(this, "OS cadastrada com sucesso.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Erro de conexão, tente novamente.", Toast.LENGTH_SHORT).show();
                    dialogCarregando.dismiss();
                }
            });

        });

       mainBinding.foto.setOnClickListener( view -> {
           baterFoto();
       });

        configurarRecyclerImages();
    }

    private void configurarDialogCarregando() {
        AlertDialog.Builder b = new AlertDialog.Builder(OSActivity.this);
        b.setView(LoadingLayoutBinding.inflate(getLayoutInflater()).getRoot());
        dialogCarregando = b.create();
    }

    public void baterFoto(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if ( checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                String[] permissions = {android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSON_CODE);
            }
            else {
                // already permisson
                openCamera();
            }
        }
        else{
            // system < M
            openCamera();
        }
    }


    public void openCamera(){
        ContentValues values  = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "nova picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Imagem tirada da câmera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intentCamera =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intentCamera, REQUEST_IMAGE_CAPTURE);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            dialogCarregando.show();
            File imageFile = ImageUtils.uriToFile(getApplicationContext(), imageUri);
            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "Image Description");
            RequestBody image = RequestBody.create(MediaType.parse("image/png"), imageFile);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), image);

            serviceIngur.postarImage("Bearer " + TOKEN, description, imagePart).enqueue(new Callback<ResponseIngurModel>() {
                @Override
                public void onResponse(Call<ResponseIngurModel> call, Response<ResponseIngurModel> response) {
                    if (response.isSuccessful()){
                        assert response.body() != null;
                        imagesLink.add(Objects.requireNonNull(response.body()).getData().getLink());
                        adapterImages.notifyDataSetChanged();
                        dialogCarregando.dismiss();

                    }
                }

                @Override
                public void onFailure(Call<ResponseIngurModel> call, Throwable t) {
                    dialogCarregando.dismiss();
                }
            });
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ( requestCode == PERMISSON_CODE){
            if( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else{
                Toast.makeText(this, "Conceda as permissões!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void configurarRecyclerImages(){
        r = mainBinding.recyclerFotos;
        r.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        r.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
        r.setHasFixedSize(true);
        adapterImages = new AdapterImages(imagesLink, getApplicationContext(), this);
        r.setAdapter(adapterImages);
    }
}