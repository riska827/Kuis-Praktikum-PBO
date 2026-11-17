
package com.mycompany.kuis2_pbo;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SistemHotel {
    private String namaHotel;
    private HashMap<String, Tamu> daftarTamu;
    private HashMap<String, Kamar> daftarKamar;
    private ArrayList<TransaksiReservasi> logTransaksi;

    public SistemHotel(String namaHotel) {
        this.namaHotel = namaHotel;
        this.daftarTamu = new HashMap<>();
        this.daftarKamar = new HashMap<>();
        this.logTransaksi = new ArrayList<>();
        System.out.println("=== Menginisialisasi Sistem: " + this.namaHotel + " ===");
    }
    
    public void tambahTamu(String id, String nama) {
        daftarTamu.put(id, new Tamu(id, nama));
    }

    public void tambahKamar(String kode, String namaKamar, int kuota) {
        daftarKamar.put(kode, new KamarHotel(kode, namaKamar, kuota));
    }

    // DIGIT 2 (VARIAN 1): Parsing format Strip & Pipe (ID-ACT-01/KD-OBJ-A/JML-3/|)
    public void jalankanTransaksiDariFile(String namaFile) throws FileNotFoundException {
        File file = new File(namaFile);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String baris = scanner.nextLine().trim();
            if (baris.isEmpty()) continue;

            String[] daftarData = baris.split("\\|");

            for (String item : daftarData) {
                try {
                    String[] bagian = item.split("/");
                    
                    String idTamu = bagian[0].substring(bagian[0].indexOf("-") + 1);
                    String kodeKamar = bagian[1].substring(bagian[1].indexOf("-") + 1);
                    int jumlah = Integer.parseInt(bagian[2].substring(bagian[2].indexOf("-") + 1));

                    Tamu tamu = daftarTamu.get(idTamu);
                    if (tamu == null) {
                        throw new TransaksiGagalException("ID Tamu '" + idTamu + "' tidak terdaftar di sistem!");
                    }

                    Kamar kamar = daftarKamar.get(kodeKamar);
                    if (kamar == null) {
                        throw new TransaksiGagalException("Kode Kamar '" + kodeKamar + "' tidak ditemukan!");
                    }

                    kamar.kurangiKuota(jumlah);
                    TransaksiReservasi trx = new TransaksiReservasi(tamu, kamar, jumlah);
                    logTransaksi.add(trx);
                    System.out.println("[BERHASIL] " + trx.getDetailLog());

                } catch (TransaksiGagalException e) {
                    System.out.println("[GAGAL TRANSAKSI] " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("[ERROR PARSING] Format data tidak valid pada: " + item);
                }
            }
        }
        scanner.close();
    }
    
    // DIGIT 3 (VARIAN 3): Serialization ke backup_data.dat & Loop normal ArrayList ke laporan.txt
    public void simpanLaporanAkhir() {
        System.out.println("\n--- MENYIMPAN LAPORAN AKHIR & SERIALIZATION ---");
        
        try {
            FileOutputStream fos = new FileOutputStream("backup_data.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.daftarTamu);
            oos.writeObject(this.daftarKamar);
            oos.writeObject(this.logTransaksi);
            oos.close();
            System.out.println("[SERIALIZATION] Data memori berhasil disimpan ke 'backup_data.dat'.");
        } catch (IOException e) {
            System.out.println("[ERROR SERIALIZATION] Gagal menyimpan data biner: " + e.getMessage());
        }

        try {
            FileWriter writer = new FileWriter("laporan.txt");
            writer.write("=== STRUK RIWAYAT TRANSAKSI " + this.namaHotel.toUpperCase() + " ===\n");
            
            for (int i = 0; i < logTransaksi.size(); i++) {
                writer.write((i + 1) + ". " + logTransaksi.get(i).getDetailLog() + "\n");
            }
            
            writer.close();
            System.out.println("[FILE I/O] Struk riwayat transaksi berhasil ditulis ke 'laporan.txt'.");
        } catch (IOException e) {
            System.out.println("[ERROR FILE I/O] Gagal menulis laporan.txt: " + e.getMessage());
        }
    }
}
