package com.baak14.javabaak14.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baak14.javabaak14.enums.JenisSurat;
import com.baak14.javabaak14.enums.StatusSurat;
import com.baak14.javabaak14.model.Surat;
import com.baak14.javabaak14.repository.SuratRepository;

@Service
public class SuratService {

    private final SuratRepository suratRepository;

    @Autowired
    public SuratService(SuratRepository suratRepository) {
        this.suratRepository = suratRepository;
    }

    public Surat findById(int id) {
        return suratRepository.findById(id).orElse(null);
    }

    public List<Surat> findAll() {
        return suratRepository.findAll();
    }

    public List<Surat> findByJenisSurat(JenisSurat jenisSurat) {
        return suratRepository.findByKeteranganSurat(jenisSurat);
    }

    public List<Surat> findByStatus(StatusSurat statusSurat) {
        return suratRepository.findByStatus(statusSurat);
    }

    public Surat save(Surat surat) {
        return suratRepository.save(surat);
    }

    public void deleteById(int id) {
        suratRepository.deleteById(id);
    }
}
