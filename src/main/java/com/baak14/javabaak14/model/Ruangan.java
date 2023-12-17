package com.baak14.javabaak14.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baak14.javabaak14.enums.RuanganStatus;


@Entity
@Table(name = "nama_ruangan")
public class Ruangan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String namaRuangan;

    @Enumerated(EnumType.STRING)
    private RuanganStatus status;

    // Konstruktor
    public Ruangan() {
    }

    public Ruangan(int id, String namaRuangan, RuanganStatus status) {
        this.id = id;
        this.namaRuangan = namaRuangan;
        this.status = status;
    }

    // Getter dan setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaRuangan() {
        return namaRuangan;
    }

    public void setNamaRuangan(String namaRuangan) {
        this.namaRuangan = namaRuangan;
    }

    public RuanganStatus getStatus() {
        return status;
    }

    public void setStatus(RuanganStatus status) {
        this.status = status;
    }
}
