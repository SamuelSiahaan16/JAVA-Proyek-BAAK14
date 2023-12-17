package com.baak14.javabaak14.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.baak14.javabaak14.enums.JenisSurat;
import com.baak14.javabaak14.enums.StatusSurat;

import java.util.Date;

@Entity
public class Surat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "keterangan_surat")
    private JenisSurat keteranganSurat; // Menggunakan enum JenisSurat

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusSurat status; // Menggunakan enum StatusSurat

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "keperluan", columnDefinition = "TEXT")
    private String keperluan;

    @Column(name = "id_user")
    private int idUser;

    // Constructors, getters, and setters

    public Surat() {
    }

    public Surat(int id, JenisSurat keteranganSurat, StatusSurat status, Date startDate, Date endDate, String keperluan, int idUser) {
        this.id = id;
        this.keteranganSurat = keteranganSurat;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.keperluan = keperluan;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JenisSurat getKeteranganSurat() {
        return keteranganSurat;
    }

    public void setKeteranganSurat(JenisSurat keteranganSurat) {
        this.keteranganSurat = keteranganSurat;
    }

    public StatusSurat getStatus() {
        return status;
    }

    public void setStatus(StatusSurat status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getKeperluan() {
        return keperluan;
    }

    public void setKeperluan(String keperluan) {
        this.keperluan = keperluan;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
