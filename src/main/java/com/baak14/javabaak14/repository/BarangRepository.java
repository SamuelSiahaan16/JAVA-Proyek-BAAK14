package com.baak14.javabaak14.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.baak14.javabaak14.model.Barang;

@Repository
public interface BarangRepository extends JpaRepository<Barang, Integer> {
    // Tambahan metode khusus jika diperlukan
}
