
package com.mycompany.kuis2_pbo;

import java.io.Serializable;

public class TransaksiReservasi implements Serializable {
    private static final long serialVersionUID = 1L;
    private Tamu tamu;
    private Kamar kamar;
    private int jumlahPesan;

    public TransaksiReservasi(Tamu tamu, Kamar kamar, int jumlahPesan) {
        this.tamu = tamu;
        this.kamar = kamar;
        this.jumlahPesan = jumlahPesan;
    }

    public String getDetailLog() {
        return "Tamu: " + tamu.getNama() + " (" + tamu.getIdTamu() + ") | Kamar: " 
               + kamar.getNamaKamar() + " [" + kamar.getKategoriLayanan() + "] | Qty: " + jumlahPesan;
    }
}
