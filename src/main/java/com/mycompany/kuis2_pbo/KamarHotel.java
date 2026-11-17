
package com.mycompany.kuis2_pbo;

public class KamarHotel extends Kamar {
    private static final long serialVersionUID = 1L;

    public KamarHotel(String kodeKamar, String namaKamar, int kuota) {
        super(kodeKamar, namaKamar, kuota);
    }

    @Override
    public String getKategoriLayanan() {
        if (namaKamar.contains("Suite")) return "VIP Hospitality";
        if (namaKamar.contains("Reguler")) return "Standard Hospitality";
        return "Basic Hospitality";
    }
}
