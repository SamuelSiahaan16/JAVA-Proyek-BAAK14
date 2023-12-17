package com.baak14.javabaak14.controller;

import com.baak14.javabaak14.model.Barang;
import com.baak14.javabaak14.service.BarangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barang")
public class BarangController {

    @Autowired
    private BarangService barangService;

    @GetMapping("/list")
    public ResponseEntity<List<Barang>> getAllBarang() {
        List<Barang> barangList = barangService.getDaftarBarang();
        return ResponseEntity.ok().body(barangList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Barang> getBarangById(@PathVariable("id") int id) {
        Barang barang = barangService.getBarangById(id);
        if (barang != null) {
            return ResponseEntity.ok().body(barang);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Barang> addBarang(@RequestBody Barang barang) {
        barangService.addBarang(barang);
        return ResponseEntity.ok().body(barang);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Barang> updateBarang(@PathVariable("id") int id, @RequestBody Barang barang) {
        Barang existingBarang = barangService.getBarangById(id);
        if (existingBarang != null) {
            barang.setId(id);
            barangService.updateBarang(barang);
            return ResponseEntity.ok().body(barang);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBarang(@PathVariable("id") int id) {
        Barang existingBarang = barangService.getBarangById(id);
        if (existingBarang != null) {
            barangService.deleteBarang(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
