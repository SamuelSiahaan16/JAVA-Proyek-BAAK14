package com.baak14.javabaak14.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.baak14.javabaak14.enums.BookingStatus;
import com.baak14.javabaak14.enums.RuanganStatus;
import com.baak14.javabaak14.model.Ruangan;
import com.baak14.javabaak14.repository.RuanganRepository;

@Service
@Transactional
public class RuanganService {
    @Autowired
    private RuanganRepository ruanganRepository;
    
    public List<Ruangan> getDaftarRuangan() {
        return ruanganRepository.findAll();
    }

    public void addRuangan(Ruangan ruangan) {
        ruanganRepository.save(ruangan);
    }

    public Ruangan getRuanganById(Integer id) {
        return ruanganRepository.findById(id).orElse(null);
    }

    public void deleteRuangan(Integer id) {
        ruanganRepository.deleteById(id);
    }

    public List<Ruangan> getRuanganByStatus(RuanganStatus ruanganStatus) {
        return ruanganRepository.findByStatus(ruanganStatus);
    }

    public void updateRuangan(Ruangan ruangan) {
        ruanganRepository.save(ruangan);
    }

    public Ruangan findById(int id) {
        return ruanganRepository.findById(id).orElse(null);
    }
 

}
