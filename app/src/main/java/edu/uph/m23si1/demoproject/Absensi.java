package edu.uph.m23si1.demoproject;

import com.google.firebase.Timestamp;

public class Absensi {
    private String nama;
    private String kelas;
    private String keterangan;
    private Timestamp tanggal;

    // Must have a public no-argument constructor for Firestore
    public Absensi() {}

    public Absensi(String nama, String kelas, String keterangan, Timestamp tanggal) {
        this.nama = nama;
        this.kelas = kelas;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Timestamp getTanggal() {
        return tanggal;
    }

    public void setTanggal(Timestamp tanggal) {
        this.tanggal = tanggal;
    }
}