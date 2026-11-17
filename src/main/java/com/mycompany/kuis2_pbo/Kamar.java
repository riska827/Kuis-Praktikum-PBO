
package com.mycompany.kuis2_pbo;

import java.io.Serializable;

public abstract class Kamar implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String kodeKamar;
    protected String namaKamar;
    protected int kuota;

    public Kamar(String kodeKamar, String namaKamar, int kuota) {
        this.kodeKamar = kodeKamar;
        this.namaKamar = namaKamar;
        this.kuota = kuota;
    }

    public String getKodeKamar() { return kodeKamar; }
    public String getNamaKamar() { return namaKamar; }
    public int getKuota() { return kuota; }

    public void kurangiKuota(int jumlah) throws TransaksiGagalException {
        if (jumlah > this.kuota) {
            throw new TransaksiGagalException("Kuota kamar " + namaKamar + " tidak mencukupi (Sisa: " + kuota + ", Diminta: " + jumlah + ")");
        }
        this.kuota -= jumlah;
    }

    public abstract String getKategoriLayanan();
}
