package com.baak14.javabaak14.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baak14.javabaak14.model.Barang;
import com.baak14.javabaak14.repository.BarangRepository;

@Service
@Transactional
public class BarangService {

    @Autowired
    private BarangRepository barangRepository;

    public List<Barang> getDaftarBarang() {
        return barangRepository.findAll();
    }

    public Barang getBarangById(int id) {
        return barangRepository.findById(id).orElse(null);
    }

    public void addBarang(Barang barang) {
        barangRepository.save(barang);
    }

    public void updateBarang(Barang barang) {
        barangRepository.save(barang);
    }

    public void deleteBarang(int id) {
        barangRepository.deleteById(id);
    }
}
