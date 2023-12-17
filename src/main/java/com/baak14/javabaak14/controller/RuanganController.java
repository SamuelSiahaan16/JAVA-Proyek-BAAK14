package com.baak14.javabaak14.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baak14.javabaak14.model.Ruangan;
import com.baak14.javabaak14.service.RuanganService;

@RestController
@RequestMapping("/ruangan")
@CrossOrigin(origins = "http://localhost:8080")
public class RuanganController {
    private final RuanganService ruanganService;

    @Autowired
    public RuanganController(RuanganService ruanganService) {
        this.ruanganService = ruanganService;
    }

    @GetMapping("/list")
    public List<Ruangan> listAllRuangan() {
        return ruanganService.getDaftarRuangan();
    }

    @GetMapping("/{id}")
    public Ruangan getRuangan(@PathVariable Integer id) {
        return ruanganService.getRuanganById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRuangan(@RequestBody Ruangan ruangan) {
        try {
            ruanganService.addRuangan(ruangan);
            return ResponseEntity.ok("{\"message\": \"Ruangan added successfully.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Failed to add ruangan. Error: " + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRuangan(@PathVariable Integer id, @RequestBody Ruangan ruangan) {
        try {
            Ruangan existingRuangan = ruanganService.getRuanganById(id);
            if (existingRuangan != null) {
                existingRuangan.setNamaRuangan(ruangan.getNamaRuangan());
                existingRuangan.setStatus(ruangan.getStatus());
                ruanganService.updateRuangan(existingRuangan);
                return ResponseEntity.ok("{\"message\": \"Ruangan updated successfully.\"}");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"Ruangan not found with ID: " + id + "\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Failed to update ruangan. Error: " + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRuangan(@PathVariable Integer id) {
        try {
            ruanganService.deleteRuangan(id);
            return ResponseEntity.ok("{\"message\": \"Ruangan deleted successfully.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Failed to delete ruangan. Error: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/searchByStatus")
    public List<Ruangan> searchRuanganByStatus(@RequestParam String status) {
        return ruanganService.getRuanganByStatus(status);
    }
}
