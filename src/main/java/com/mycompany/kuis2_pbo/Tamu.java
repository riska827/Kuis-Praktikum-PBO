
package com.mycompany.kuis2_pbo;

import java.io.Serializable;

public class Tamu implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idTamu;
    private String nama;

    public Tamu(String idTamu, String nama) {
        this.idTamu = idTamu;
        this.nama = nama;
    }

    public String getIdTamu() { return idTamu; }
    public String getNama() { return nama; }
}
