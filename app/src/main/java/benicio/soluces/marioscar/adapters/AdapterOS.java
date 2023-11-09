package benicio.soluces.marioscar.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import benicio.soluces.marioscar.R;
import benicio.soluces.marioscar.model.OSModel;
import kotlin.jvm.internal.Lambda;

public class AdapterOS extends RecyclerView.Adapter<AdapterOS.MyViewHolder> {
    List<OSModel> oss;
    Activity a;
    Context c;

    public AdapterOS(List<OSModel> oss, Activity a, Context c) {
        this.oss = oss;
        this.a = a;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.os_layout, parent, false));
    }

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
    }

    @Override
    public int getItemCount() {
        return oss.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView infos;
        RecyclerView r;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            infos = itemView.findViewById(R.id.infos_os);
            r = itemView.findViewById(R.id.recycler_os);
        }
    }
}
