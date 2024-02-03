package benicio.soluces.marioscar.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.system.Os;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import benicio.soluces.marioscar.OSActivity;
import benicio.soluces.marioscar.R;
import benicio.soluces.marioscar.databinding.LoadingLayoutBinding;
import benicio.soluces.marioscar.model.ItemModel;
import benicio.soluces.marioscar.model.OSModel;
import benicio.soluces.marioscar.model.UsuarioModel;
import benicio.soluces.marioscar.utils.DatabaseUtils;
import kotlin.jvm.internal.Lambda;

public class AdapterOS extends RecyclerView.Adapter<AdapterOS.MyViewHolder> {
    private DatabaseReference refOs = FirebaseDatabase.getInstance().getReference().getRef().child(DatabaseUtils.OS_DB);
    int posFotoX;
    int posFotoY;
    List<Bitmap> bitmaps = new ArrayList<>();
    List<OSModel> oss;
    Activity a;
    Context c;

    Dialog d;

    public interface OnPdfTaskCompleted {
        void onTaskCompleted();
    }
    public AdapterOS(List<OSModel> oss, Activity a, Context c) {
        this.oss = oss;
        this.a = a;
        this.c = c;
        AlertDialog.Builder b = new AlertDialog.Builder(a);
        b.setView(LoadingLayoutBinding.inflate(a.getLayoutInflater()).getRoot());
        this.d = b.create();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.os_layout, parent, false));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OSModel osModel = oss.get(position);
        holder.infos.setText(
                osModel.toString()
        );

        holder.r.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false));
        holder.r.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.HORIZONTAL));
        holder.r.setHasFixedSize(true);
        holder.r.setAdapter(new AdapterImages(osModel.getFotos(),c,a));

        holder.layoutAdmin.setVisibility(View.VISIBLE);

        holder.editarOS.setOnClickListener( view -> {
            Intent i = new Intent(c, OSActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("isEdit", true);
            i.putExtra("idOS", osModel.getId());
            c.startActivity(i);
        });

        holder.excluirOS.setOnClickListener(view -> {
            osModel.setDeletado(true);

            refOs.child(osModel.getId()).setValue(osModel).addOnCompleteListener( task -> {
                if ( task.isSuccessful() ){
                    Toast.makeText(a, "OS excluída", Toast.LENGTH_SHORT).show();
                    oss.remove(position);
                    this.notifyDataSetChanged();
                }
            });
        });

        holder.compartilharOS.setOnClickListener( view -> {
            d.show();
            gerarPdfOS(osModel);
        });

    }

    @Override
    public int getItemCount() {
        return oss.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView infos;
        RecyclerView r;
        LinearLayout layoutAdmin;
        Button compartilharOS, editarOS, excluirOS;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            infos = itemView.findViewById(R.id.infos_os);
            r = itemView.findViewById(R.id.recycler_os);
            layoutAdmin = itemView.findViewById(R.id.admin_layout);
            compartilharOS = itemView.findViewById(R.id.compartilharosbtn);
            editarOS = itemView.findViewById(R.id.editarosbtn);
            excluirOS = itemView.findViewById(R.id.excluirosbtn);
        }
    }
    private class CreateBitmapTask extends AsyncTask<Void, Void, Void> {
        List<String> urlImages;
        private OnPdfTaskCompleted callback;
        public CreateBitmapTask(List<String> urlImages, OnPdfTaskCompleted callback) {
            this.urlImages = urlImages;
            this.callback = callback;
        }
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if (callback != null) {
                callback.onTaskCompleted();
            }
        }
        @Override
        protected Void doInBackground(Void... voids) {
            for (String urlImage : this.urlImages){
                bitmaps.add(downloadImage(urlImage));
            }
            return null;
        }
        private Bitmap downloadImage(String imageUrl) {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void gerarPdfOS(OSModel osModel){
        int ESPACAMENTO_PADRAO = 10;

        Bitmap bmpTemplate = BitmapFactory.decodeResource(a.getResources(), R.raw.templaterelatorio);
        Bitmap scaledbmpTemplate = Bitmap.createScaledBitmap(bmpTemplate, 792, 1120, false);
        int pageHeight = 1120;
        int pagewidth = 792;

        PdfDocument pdfDocument = new PdfDocument();

        Paint paint = new Paint();
        Paint title = new Paint();
        Paint restante = new Paint();

        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        Canvas canvas = myPage.getCanvas();

        canvas.drawBitmap(scaledbmpTemplate, 1, 1, paint);

        title.setTextSize(16);
        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        title.setColor(ContextCompat.getColor(a, R.color.black));

        restante.setTextSize(10);
        restante.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        restante.setColor(ContextCompat.getColor(a, R.color.black));

        canvas.drawText(osModel.getNumeroOs(), 264, 88, title);
        canvas.drawText(osModel.getData(), 571, 88, title);

        int posClienteX = 120;
        int posClientey = 140;

        canvas.drawText("Dados do cliente: ", posClienteX, posClientey, title);
        posClientey += ESPACAMENTO_PADRAO;

        for ( String linhaCliente : osModel.getUsuarioModel().toString().split("\n")){
            posClientey += 10;
            canvas.drawText(linhaCliente, posClienteX, posClientey, restante);
        }
        int posVeiculoX = posClienteX;
        int posVeiculoY = posClientey + 20;

        canvas.drawText("Dados do veículo: ", posVeiculoX, posVeiculoY, title);
        posVeiculoY += ESPACAMENTO_PADRAO;

        for ( String linhaVeiculo : osModel.getVeiculoModel().toString().split("\n")){
            posVeiculoY += 10;
            canvas.drawText(linhaVeiculo, posVeiculoX, posVeiculoY, restante);
        }

        int posPecasX = posVeiculoX;
        int posPecasY = posVeiculoY + 20;

        canvas.drawText("Peças da OS: ", posPecasX, posPecasY, title);
        posPecasY += ESPACAMENTO_PADRAO;

        for (ItemModel item : osModel.getItens()){
            posPecasY += 10;
            canvas.drawText(item.toString(), posPecasX, posPecasY, restante);
        }

        int posServicosX = posPecasX;
        int posServicosY = posPecasY + 20;

        canvas.drawText("Serviços da OS: ", posServicosX, posServicosY, title);
        posServicosY += ESPACAMENTO_PADRAO;

        for (ItemModel item : osModel.getServicos()){
            posServicosY += 10;
            canvas.drawText(item.toString(), posServicosX, posServicosY, restante);
        }

        int posDescricaoX = posServicosX;
        int posDescricaoY = posServicosY + 20;

        canvas.drawText("Descrição completa: ", posDescricaoX, posDescricaoY, title);
        posDescricaoY += ESPACAMENTO_PADRAO;

        for ( String linhaDaOs : osModel.toString().split("\n")) {
            posDescricaoY += 10;
            canvas.drawText(linhaDaOs, posDescricaoX, posDescricaoY, restante);
        }

        posFotoX = posDescricaoX;
        posFotoY = posDescricaoY + 20;

        canvas.drawText("Fotos: ", 120, posFotoY, title);
        posFotoY += ESPACAMENTO_PADRAO;

        new CreateBitmapTask(osModel.getFotos(), () -> {

            for ( Bitmap bitmap : bitmaps){
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
                canvas.drawBitmap( resizedBitmap, posFotoX, posFotoY, paint);
                posFotoX += 130;
            }

            pdfDocument.finishPage(myPage);

            File documentosDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

            File kaizenProjetosDir = new File(documentosDir, "MARIOSCAR");
            if (!kaizenProjetosDir.exists()) {
                kaizenProjetosDir.mkdirs();
            }

            String nomeArquivo = "os_" + osModel.getNumeroOs().replace(" ", "_") + "_" + osModel.getData().replace("/", "_") + ".pdf";
            File file = new File(kaizenProjetosDir, nomeArquivo);
            try {
                pdfDocument.writeTo(new FileOutputStream(file));
                Toast.makeText(a, "PDF salvo em Documents/MARDIESEL", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                AlertDialog.Builder b = new AlertDialog.Builder(a);
                b.setTitle("Aviso");
                b.setMessage(e.getMessage());
                b.setPositiveButton("Fechar", null);
                b.create().show();
                e.printStackTrace();
            }
            pdfDocument.close();
            d.dismiss();

            compartilharPDFViaWhatsApp(file);

        }).execute();
    }

    private void compartilharPDFViaWhatsApp(File file) {

        Uri contentUri = FileProvider.getUriForFile(a, a.getPackageName() + ".provider", file);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_STREAM, contentUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Tenta compartilhar no WhatsApp normal
        intent.setPackage("com.whatsapp.w4b");
        try {
            a.startActivity(intent);
            return; // Se não houver exceção, o compartilhamento foi bem-sucedido
        } catch (android.content.ActivityNotFoundException e) {
            // WhatsApp normal não instalado, continua para o próximo bloco
        }

        // Tenta compartilhar no WhatsApp Business
        intent.setPackage("com.whatsapp");

        try {
            a.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            // WhatsApp Business não instalado
            Toast.makeText(a, "WhatsApp e WhatsApp Business não estão instalados", Toast.LENGTH_SHORT).show();
        }
    }
}
