package edu.uph.m23si1.demoproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.uph.m23si1.demoproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private AbsensiAdapter adapter;
    private List<Absensi> absensiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        setupSpinner();
        setupRecyclerView();

        binding.buttonSimpan.setOnClickListener(v -> saveAbsensi());
        binding.buttonLogout.setOnClickListener(v -> logout());

        loadAbsensiData();
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.keterangan_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerKeterangan.setAdapter(spinnerAdapter);
    }

    private void setupRecyclerView() {
        absensiList = new ArrayList<>();
        adapter = new AbsensiAdapter(absensiList, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }

    private void saveAbsensi() {
        String nama = binding.editNama.getText().toString().trim();
        String kelas = binding.editKelas.getText().toString().trim();
        String keterangan = binding.spinnerKeterangan.getSelectedItem().toString();

        if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(kelas)) {
            Toast.makeText(this, "Nama dan Kelas tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> absensi = new HashMap<>();
        absensi.put("nama", nama);
        absensi.put("kelas", kelas);
        absensi.put("keterangan", keterangan);
        absensi.put("tanggal", Timestamp.now());

        db.collection("absensi")
                .add(absensi)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Absensi disimpan", Toast.LENGTH_SHORT).show();
                    binding.editNama.setText("");
                    binding.editKelas.setText("");
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error saving: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void loadAbsensiData() {
        db.collection("absensi")
                .orderBy("tanggal", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.w("MainActivity", "Listen failed.", error);
                        return;
                    }

                    absensiList.clear();
                    if (value != null) {
                        for (QueryDocumentSnapshot doc : value) {
                            Absensi absensi = doc.toObject(Absensi.class);
                            absensiList.add(absensi);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}