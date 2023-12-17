package com.baak14.javabaak14.controller;
 
import com.baak14.javabaak14.enums.StatusSurat;
import com.baak14.javabaak14.model.Surat;
import com.baak14.javabaak14.service.SuratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/surat")
public class SuratController {

    @Autowired
    private SuratService suratService;

    @GetMapping("/list")
    public ResponseEntity<List<Surat>> getAllSurat() {
        List<Surat> suratList = suratService.findAll();
        return ResponseEntity.ok().body(suratList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Surat> getSuratById(@PathVariable("id") int id) {
        Surat surat = suratService.findById(id);
        if (surat != null) {
            return ResponseEntity.ok().body(surat);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Surat> addSurat(@RequestBody Surat surat) {
        suratService.save(surat);
        return ResponseEntity.ok().body(surat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Surat> updateSurat(@PathVariable("id") int id, @RequestBody Surat surat) {
        Surat existingSurat = suratService.findById(id);
        if (existingSurat != null) {
            surat.setId(id);
            suratService.save(surat);
            return ResponseEntity.ok().body(surat);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurat(@PathVariable("id") int id) {
        Surat existingSurat = suratService.findById(id);
        if (existingSurat != null) {
            suratService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/ubah-status")
    public ResponseEntity<Surat> ubahStatusSurat(@PathVariable("id") int id, @RequestParam("status") StatusSurat status) {
        Surat existingSurat = suratService.findById(id);
        if (existingSurat != null) {
            existingSurat.setStatus(status);
            suratService.save(existingSurat);
            return ResponseEntity.ok().body(existingSurat);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
