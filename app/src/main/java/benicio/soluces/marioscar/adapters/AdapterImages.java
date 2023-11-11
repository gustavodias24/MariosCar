package benicio.soluces.marioscar.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import benicio.soluces.marioscar.R;
import benicio.soluces.marioscar.databinding.ExibirImageAoClicarLayoutBinding;
import benicio.soluces.marioscar.databinding.ImageLayoutBinding;

public class AdapterImages extends RecyclerView.Adapter<AdapterImages.MyViewHolder>{
    List<String> images;
    Context c;
    Activity a;

    public AdapterImages(List<String> images, Context c, Activity a) {
        this.images = images;
        this.c = c;
        this.a = a;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout, parent, false));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String link = images.get(position);
        Picasso.get().load(link).into(
                holder.imagePreview
        );

        holder.itemView.getRootView().setOnClickListener( view -> {
            AlertDialog.Builder b = new AlertDialog.Builder(a);
            ExibirImageAoClicarLayoutBinding imageLayoutBinding = ExibirImageAoClicarLayoutBinding.inflate(a.getLayoutInflater());
            Picasso.get().load(link).into(imageLayoutBinding.imagemPreview);
            b.setView(imageLayoutBinding.getRoot());
            b.create().show();
        });

        holder.itemView.getRootView().setOnLongClickListener( view -> {
            images.remove(position);
            Toast.makeText(c, "Item removido", Toast.LENGTH_SHORT).show();
            this.notifyDataSetChanged();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePreview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePreview = itemView.findViewById(R.id.imagem_preview);
        }
    }
}
