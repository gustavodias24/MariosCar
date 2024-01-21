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
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import benicio.soluces.marioscar.adapters.AdapterImages;
import benicio.soluces.marioscar.adapters.AdapterItens;
import benicio.soluces.marioscar.databinding.ActivityOsactivityBinding;
import benicio.soluces.marioscar.databinding.LayoutAdicionarItemBinding;
import benicio.soluces.marioscar.databinding.LoadingLayoutBinding;
import benicio.soluces.marioscar.databinding.SelectCameraOrGaleryLayoutBinding;
import benicio.soluces.marioscar.model.ItemModel;
import benicio.soluces.marioscar.model.OSModel;
import benicio.soluces.marioscar.model.ResponseIngurModel;
import benicio.soluces.marioscar.model.UsuarioModel;
import benicio.soluces.marioscar.model.VeiculoModel;
import benicio.soluces.marioscar.utils.ImageUtils;
import benicio.soluces.marioscar.utils.MathUtils;
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
    private DatabaseReference refClientes = FirebaseDatabase.getInstance().getReference().getRef().child("clientes");
    private DatabaseReference refVeiculos = FirebaseDatabase.getInstance().getReference().getRef().child("veiculos");
    private DatabaseReference refOs = FirebaseDatabase.getInstance().getReference().getRef().child("os");

    private Dialog  dialogSelecionarFoto;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final int PICK_IMAGE_REQUEST = 2;
    private String id;
    Uri imageUri;
    private Dialog dialogCarregando, dialogAdicionarItem, dialogAdicionarServico;
    private static final int PERMISSON_CODE = 1000;
    private static final String TOKEN = "282c6d7932402b7665da78ee7c51311556ce6c8a";
    private ActivityOsactivityBinding mainBinding;
    Retrofit retrofitIngur = RetrofitUtils.createRetrofitIngur();
    ServiceIngur serviceIngur = RetrofitUtils.createServiceIngur(retrofitIngur);

    AdapterImages adapterImages;
    AdapterItens adapterItens;
    AdapterItens adapterServicos;
    RecyclerView r;
    RecyclerView rItens;
    RecyclerView rServicos;
    List<String> imagesLink = new ArrayList<>();
    private Bundle b;
    List<ItemModel> itens = new ArrayList<>();
    List<ItemModel> servicos = new ArrayList<>();
    int numeroOs = 0;
    String idCarro ;
    String placaCarro ;
    String idCliente ;

    @SuppressLint("SetTextI18n")
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
        configurarDialogSelecionarFoto();

        if ( b.getBoolean("isEdit", false)){
            mainBinding.cadastrar.setText("Conluir a edição");
            refOs.child(Objects.requireNonNull(b.getString("idOS"))).get().addOnCompleteListener(task -> {
                if ( task.isSuccessful() ){
                    OSModel os = task.getResult().getValue(OSModel.class);
                    mainBinding.descricaoField.getEditText().setText(os.getDescricao());
                    mainBinding.descricaoPeAField.getEditText().setText(os.getDescricaoPeca());
                    mainBinding.valorTotalPecasField.getEditText().setText(os.getValorTotalPecas());
                    mainBinding.valorServicoField.getEditText().setText(os.getValorService());
                    mainBinding.descontoField.getEditText().setText(os.getDesconto());
                    mainBinding.totalField.getEditText().setText(os.getTotal());
                    mainBinding.obsField.getEditText().setText(os.getObs());

                    imagesLink.addAll(os.getFotos());
                    adapterImages.notifyDataSetChanged();
                    itens.addAll(os.getItens());
                    adapterItens.notifyDataSetChanged();

                    mainBinding.bateria.setChecked(os.getBateria());
                    mainBinding.alarme.setChecked(os.getAlarme());
                    mainBinding.buzina.setChecked(os.getBuzina());
                    mainBinding.trava.setChecked(os.getTrava());
                    mainBinding.vidro.setChecked(os.getVidro());
                    mainBinding.tapete.setChecked(os.getTapete());
                    mainBinding.chaveRoda.setChecked(os.getChaveRoda());
                    mainBinding.macaco.setChecked(os.getMacaco());
                    mainBinding.triangulo.setChecked(os.getTriangulo());
                    mainBinding.extintor.setChecked(os.getExtintor());
                    mainBinding.som.setChecked(os.getSom());

                    idCarro = os.getIdCarro();
                    idCliente = os.getIdCliente();
                    placaCarro = os.getPlacaCarro();
                    numeroOs = Integer.parseInt(os.getNumeroOs());
                }else{
                    Toast.makeText(OSActivity.this, "Erro de conexão!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        }

        refOs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (numeroOs == 0){
                    numeroOs = 1;
                    if ( snapshot.exists() ){
                        for ( DataSnapshot d : snapshot.getChildren()){
                            numeroOs++;
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       mainBinding.cadastrar.setOnClickListener(view -> {
            dialogCarregando.show();
            Toast.makeText(this, "Aguarde!", Toast.LENGTH_SHORT).show();

            if ( !b.getBoolean("isEdit", false)){
                id = Base64.getEncoder().encodeToString(
                        UUID.randomUUID().toString().getBytes()
                );
            }else{
                id = b.getString("idOS");
            }

            String idCarro = b.getString("idCarro", "");
            String placaCarro = b.getString("placaCarro", "");
            String idCliente = b.getString("idCliente", "");

            String descricao, descricaoPeca, valorTotal, valorService, desconto, total, obs, valorTotalPecas;

            descricao = mainBinding.descricaoField.getEditText().getText().toString();
            descricaoPeca = mainBinding.descricaoPeAField.getEditText().getText().toString();
            valorTotalPecas = mainBinding.valorTotalPecasField.getEditText().getText().toString();
            valorService = mainBinding.valorServicoField.getEditText().getText().toString();
            desconto = mainBinding.descontoField.getEditText().getText().toString();
            total = mainBinding.totalField.getEditText().getText().toString();
            obs = mainBinding.obsField.getEditText().getText().toString();


            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            refClientes.child(idCliente).get().addOnCompleteListener(task -> {
                if ( task.isSuccessful() ){
                    UsuarioModel cliente = task.getResult().getValue(UsuarioModel.class);
                    refVeiculos.child(idCarro).get().addOnCompleteListener(task1 -> {
                        if ( task1.isSuccessful() ){
                            VeiculoModel veiculo = task1.getResult().getValue(VeiculoModel.class);
                            refOs.child(id).setValue(
                                    new OSModel(
                                            veiculo,
                                            cliente,
                                            idCliente,
                                            servicos,
                                            padWithZeros(numeroOs+"" , 6),
                                            dateFormat.format(currentDate),
                                            itens,
                                            valorTotalPecas,
                                            placaCarro,
                                            id,
                                            idCarro,
                                            descricao, descricaoPeca,
                                            total, valorService, desconto, total, obs,
                                            imagesLink,
                                            mainBinding.bateria.isChecked(),
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
                            ).addOnCompleteListener(task2 -> {
                                if ( task.isSuccessful() ){
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    if ( !b.getBoolean("isEdit", false)){
                                        Toast.makeText(getApplicationContext(), "OS cadastrada com sucesso.", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "OS atualizada com sucesso.", Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Log.d("firebaseBucetinha", "onDataChange: " + task.getException().getMessage());
                                    Log.d("firebaseBucetinha", "onDataChange: " + task.getException().getCause());
                                    Toast.makeText(getApplicationContext(), "Erro de conexão, tente novamente.", Toast.LENGTH_LONG).show();
                                    dialogCarregando.dismiss();
                                }
                            });
                        }else{
                            Toast.makeText(OSActivity.this, "Problema de conexão", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(OSActivity.this, "Problema de conexão", Toast.LENGTH_SHORT).show();
                }
            });
        });

       mainBinding.selecionarFoto.setOnClickListener( view -> {
            dialogSelecionarFoto.show();
       });

       mainBinding.adicionarPeca.setOnClickListener( view -> {
           dialogAdicionarItem.show();
       });

       mainBinding.adicionarServico.setOnClickListener( view -> {
           dialogAdicionarServico.show();
       });

       mainBinding.btnCalcularTudo.setOnClickListener( view -> {
           Double desconto,valorServico, valorPeca;

           try{
               desconto = Double.parseDouble(mainBinding.descontoField.getEditText().getText().toString().replace("R$", "").replace(" ", ""));

           }catch (Exception e){
               desconto = 0.0;
           }

           try{
               valorServico = Double.parseDouble(mainBinding.valorServicoField.getEditText().getText().toString().replace("R$", "").replace(" ", ""));

           }catch (Exception e){
               valorServico = 0.0;
           }

           try{
               valorPeca = Double.parseDouble(mainBinding.valorTotalPecasField.getEditText().getText().toString().replace("R$", "").replace(" ", ""));

           }catch (Exception e){
                valorPeca =0.0;
           }

           Double precoCalculado = (valorServico + valorPeca) - desconto;

           mainBinding.totalField.getEditText().setText(
                   precoCalculado + ""
           );
       });

        configurarRecyclerImages();
        configurarRecyclerItens();
        configurarRecyclerServicos();
        configurarDialogProduto();
        configurarDialogServico();
    }

    private void configurarDialogSelecionarFoto() {
        AlertDialog.Builder b = new AlertDialog.Builder(OSActivity.this);
        SelectCameraOrGaleryLayoutBinding cameraOrGalery = SelectCameraOrGaleryLayoutBinding.inflate(getLayoutInflater());
        b.setTitle("Selecione: ");

        cameraOrGalery.btnCamera.setOnClickListener( view -> {
            baterFoto();
            dialogSelecionarFoto.dismiss();
        });

        cameraOrGalery.btnGaleria.setOnClickListener( view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
            dialogSelecionarFoto.dismiss();
        });

        b.setView(cameraOrGalery.getRoot());
        dialogSelecionarFoto = b.create();
    }

    private void configurarRecyclerItens() {
        rItens = mainBinding.recyclerPecas;
        rItens.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rItens.setHasFixedSize(true);
        rItens.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        adapterItens = new AdapterItens(itens, getApplicationContext(), mainBinding.valorTotalPecasField.getEditText(), false);
        rItens.setAdapter(adapterItens);
    }

    private void configurarRecyclerServicos() {
        rServicos = mainBinding.recyclerServicos;
        rServicos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rServicos.setHasFixedSize(true);
        rServicos.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        adapterServicos = new AdapterItens(servicos, getApplicationContext(), mainBinding.valorServicoField.getEditText(), true);
        rServicos.setAdapter(adapterServicos);
    }

    @SuppressLint("SetTextI18n")
    public  void calcularValor(EditText editValorExistente, float novoValor){
        String valorAtual = editValorExistente.getText().toString().trim().replace("R$", "").replace(" ", "");

        if ( valorAtual.isEmpty() ){
            editValorExistente.setText(novoValor + "");
        }else{
            float somaTotal = Float.parseFloat(valorAtual) + novoValor;
            editValorExistente.setText(
                    somaTotal + ""
            );
        }
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void configurarDialogProduto(){
        AlertDialog.Builder b = new AlertDialog.Builder(OSActivity.this);
        b.setTitle("Adicionar peça");
        LayoutAdicionarItemBinding adicionarItemBinding = LayoutAdicionarItemBinding.inflate(getLayoutInflater());

        String[] pecasComplete = {
                "Bomba de óleo", "Bomba d água", "Filtro lubrificante", "Filtro de ar",
                "Filtro de combustível", "Filtro de cabine", "Óleo Lubrificante",
                "Kit correia dentada", "Correia poliv", "Polia do alternador", "Junta da tampa de válvula",
                "Junta de escp", "Junta de admissão", "Jogo de junta superior", "Jogo de junta completo",
                "Retentor do comando", "Retentor do volante", "Silicone Alta temperatura", "Retentor da polia",
                "Câmara d água", "Anti chama", "Reservatório de água", "Aditivo concentrado",
                "Água destilada", "Desengripante", "Descarbonizante", "Jogo de pistão",
                "Jogo de bronze de biela", "Jogo de bronze de chumaceira", "Jogo de seguimento",
                "Base do motor", "Base da caixa de marcha", "Cano d água", "Válvula termostática",
                "Cola juntas", "Amortecedor dianteiro", "Amortecedor traseiro", "Kit coxim dianteiro",
                "Coxim dianteiro", "Kit batedor dianteiro", "Coxim traseiro", "Batedor traseiro",
                "Bieleta", "Pivô de suspensão", "Terminal", "Braço de direção", "Bucha da estabilizadora",
                "Bucha da sapata", "Barra tensora", "Suporte da barra tensora", "Jogo de cabo de vela",
                "Jogo de vela", "Jogo de pastilhas", "Jogo de disco", "Kit embreagem", "Atuador do pedal",
                "Atuador da caixa", "Caixa de direção", "Lâmpada"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, pecasComplete);

        adicionarItemBinding.nomeField.setAdapter(adapter);

        adicionarItemBinding.nomeField.setOnClickListener( view -> adicionarItemBinding.nomeField.showDropDown());

        adicionarItemBinding.nomeField.setOnItemClickListener((parent, view, position, id) -> {
            String selectedFrase = (String) parent.getItemAtPosition(position);
            adicionarItemBinding.nomeField.setText(selectedFrase);

            // Você pode fazer qualquer coisa com a frase selecionada aqui
            Toast.makeText(getApplicationContext(),  selectedFrase + " selecionado!", Toast.LENGTH_SHORT).show();
        });

        adicionarItemBinding.adicionarPeca.setOnClickListener( view -> {
            String nomePeca = adicionarItemBinding.nomeField.getText().toString();
            String quantidadeString = adicionarItemBinding.quantiadeField.getEditText().getText().toString();
            String precoString = adicionarItemBinding.valorField.getEditText().getText().toString();




            quantidadeString = quantidadeString.isEmpty() ? "0.0" : quantidadeString;
            precoString = precoString.isEmpty() ? "0.0" : precoString;

            float quantiade = Float.parseFloat(quantidadeString);
            float  preco = Float.parseFloat(precoString);
            ItemModel pecaModel = new ItemModel(nomePeca, preco, quantiade);

            calcularValor(
                    Objects.requireNonNull(mainBinding.valorTotalPecasField.getEditText()),
                    pecaModel.getValorPecaMultipl()
            );

            itens.add(pecaModel);
            adapterItens.notifyDataSetChanged();
            dialogAdicionarItem.dismiss();
            adicionarItemBinding.nomeField.setText("");
            adicionarItemBinding.quantiadeField.getEditText().setText("0");
            adicionarItemBinding.valorField.getEditText().setText("0");
        });
        b.setView(adicionarItemBinding.getRoot());
        dialogAdicionarItem  = b.create();
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void configurarDialogServico(){
        AlertDialog.Builder b = new AlertDialog.Builder(OSActivity.this);
        b.setTitle("Adicionar serviço");
        LayoutAdicionarItemBinding adicionarItemBinding = LayoutAdicionarItemBinding.inflate(getLayoutInflater());


        String[] servicosComplete = {
                "Remoção e instalação do Cabeçote",
                "Remoção e instalação do motor",
                "Remoção, Desmontagem, Montagem e instalação do Motor",
                "Remoção e instalação do amortecedor",
                "Remoção e instalação do coxim",
                "Remoção e instalação do kit batedor",
                "Remoção e instalação da bieleta",
                "Remoção e instalação do pivô",
                "Remoção e instalação dos braços de direção",
                "Remoção e instalação do terminal",
                "Remoção e instalação da bucha da sapata",
                "Remoção e instalação da bucha estabilizadora",
                "Remoção e instalação das pastilhas de freio",
                "Remoção e instalação de Disco e pastilhas",
                "Remoção e instalação da correia dentada",
                "Remoção e instalação da carreia do alternador",
                "Remoção e instalação da bomba d água",
                "Remoção e instalação da tampa de válvula",
                "Remoção e instalação do tanque de combustível",
                "Remoção e instalação da bomba, bico injetor",
                "Limpeza do Raill e do sistema de combustível",
                "Remoção e instalação da câmara d água",
                "Remoção e instalação do rolamento dianteiro",
                "Remoção e instalação do rolamento traseiro",
                "Remoção e instalação da junta homocinética",
                "Injeção eletrônica Completa",
                "Remoção e instalação do bico injetor",
                "Remoção e instalação do cilindro mestre",
                "Remoção e instalação do kit de embreagem",
                "Remoção e instalação do carter",
                "Limpeza do sistema de freio",
                "Limpeza do sistema hidráulico",
                "Limpeza do sistema de arrefecimento",
                "Remoção e instalação do trocador de calor",
                "Remoção e instalação da caixa de direção",
                "Remoção e instalação da caixa de marcha",
                "Remoção e instalação da alavanca",
                "Remoção e instalação da base do motor",
                "Remoção e instalação da bucha do feixo de mola",
                "Arquivamento de mola"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, servicosComplete);

        adicionarItemBinding.nomeField.setAdapter(adapter);

        adicionarItemBinding.nomeField.setOnClickListener( view -> adicionarItemBinding.nomeField.showDropDown());
        adicionarItemBinding.nomeField.setOnItemClickListener((parent, view, position, id) -> {

            String selectedFrase = (String) parent.getItemAtPosition(position);
            adicionarItemBinding.nomeField.setText(selectedFrase);

            // Você pode fazer qualquer coisa com a frase selecionada aqui
            Toast.makeText(getApplicationContext(),  selectedFrase + " selecionado!", Toast.LENGTH_SHORT).show();
        });


        adicionarItemBinding.nomeField.setHint("Serviço");
        adicionarItemBinding.valorField.setHint("Valor do serviço $");
//        adicionarItemBinding.quantiadeField.setVisibility(View.GONE);

        adicionarItemBinding.adicionarPeca.setOnClickListener( view -> {
            String nomeServico = adicionarItemBinding.nomeField.getText().toString();
            String precoString = adicionarItemBinding.valorField.getEditText().getText().toString();
            String quantidadeString = adicionarItemBinding.quantiadeField.getEditText().getText().toString();

            precoString = precoString.isEmpty() ? "0.0" : precoString;
            quantidadeString = quantidadeString.isEmpty() ? "0.0" : quantidadeString;
            float  preco = Float.parseFloat(precoString);
            float quantidade = Float.parseFloat(quantidadeString);

            ItemModel serviceModel = new ItemModel(nomeServico, preco, quantidade);

            calcularValor(
                    Objects.requireNonNull(mainBinding.valorServicoField.getEditText()),
                    serviceModel.getValorPecaMultipl()
            );

            servicos.add(serviceModel);
            adapterServicos.notifyDataSetChanged();
            dialogAdicionarServico.dismiss();
            adicionarItemBinding.nomeField.setText("");
            adicionarItemBinding.valorField.getEditText().setText("0");
            adicionarItemBinding.quantiadeField.getEditText().setText("0");
        });
        b.setView(adicionarItemBinding.getRoot());
        dialogAdicionarServico  = b.create();
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
        else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null){
            dialogCarregando.show();
            File imageFile = ImageUtils.uriToFile(getApplicationContext(), Objects.requireNonNull(data.getData()));
            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "Image Description");
            assert imageFile != null;
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if ( item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static String padWithZeros(String input, int desiredLength) {
        StringBuilder result = new StringBuilder(input);

        while (result.length() < desiredLength) {
            result.insert(0, "0");
        }

        return result.toString();
    }
}