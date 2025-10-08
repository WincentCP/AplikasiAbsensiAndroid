package edu.uph.m23si1.demoproject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AbsensiAdapter extends RecyclerView.Adapter<AbsensiAdapter.AbsensiViewHolder> {

    private List<Absensi> absensiList;
    private Context context;

    public AbsensiAdapter(List<Absensi> absensiList, Context context) {
        this.absensiList = absensiList;
        this.context = context;
    }

    @NonNull
    @Override
    public AbsensiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_absensi, parent, false);
        return new AbsensiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsensiViewHolder holder, int position) {
        Absensi absensi = absensiList.get(position);
        holder.textNama.setText(absensi.getNama());
        holder.textKelas.setText(absensi.getKelas());
        holder.textKeterangan.setText(absensi.getKeterangan());

        // Format the date
        if (absensi.getTanggal() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            holder.textTanggal.setText(sdf.format(absensi.getTanggal().toDate()));
        }

        // Set background color based on status
        GradientDrawable background = (GradientDrawable) holder.textKeterangan.getBackground();
        String keterangan = absensi.getKeterangan();
        if ("Hadir".equalsIgnoreCase(keterangan)) {
            background.setColor(Color.parseColor("#28A745")); // Green
        } else if ("Izin".equalsIgnoreCase(keterangan)) {
            background.setColor(Color.parseColor("#FFC107")); // Yellow
        } else if ("Sakit".equalsIgnoreCase(keterangan)) {
            background.setColor(Color.parseColor("#DC3545")); // Red
        } else {
            background.setColor(Color.parseColor("#6C757D")); // Gray
        }
    }

    @Override
    public int getItemCount() {
        return absensiList.size();
    }

    public static class AbsensiViewHolder extends RecyclerView.ViewHolder {
        TextView textNama, textKelas, textKeterangan, textTanggal;

        public AbsensiViewHolder(@NonNull View itemView) {
            super(itemView);
            textNama = itemView.findViewById(R.id.textNama);
            textKelas = itemView.findViewById(R.id.textKelas);
            textKeterangan = itemView.findViewById(R.id.textKeterangan);
            textTanggal = itemView.findViewById(R.id.textTanggal);
        }
    }
}