package com.baak14.javabaak14.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.baak14.javabaak14.enums.Ukuran;

import javax.persistence.EnumType;
import javax.persistence.Enumerated; 

@Entity
public class Barang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Ukuran ukuran;

    @Column(name = "harga_barang")
    private Double hargaBarang;

    // Constructors, getters, and setters

    public Barang() {
    }

    public Barang(int id, Ukuran ukuran, Double hargaBarang) {
        this.id = id;
        this.ukuran = ukuran;
        this.hargaBarang = hargaBarang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ukuran getUkuran() {
        return ukuran;
    }

    public void setUkuran(Ukuran ukuran) {
        this.ukuran = ukuran;
    }

    public Double getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(Double hargaBarang) {
        this.hargaBarang = hargaBarang;
    }
}
