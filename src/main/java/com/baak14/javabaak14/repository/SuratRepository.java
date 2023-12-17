package com.baak14.javabaak14.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baak14.javabaak14.enums.JenisSurat;
import com.baak14.javabaak14.enums.StatusSurat;
import com.baak14.javabaak14.model.Surat;

@Repository
public interface SuratRepository extends JpaRepository<Surat, Integer> {

    List<Surat> findByKeteranganSurat(JenisSurat jenisSurat);

    List<Surat> findByStatus(StatusSurat statusSurat);
}
