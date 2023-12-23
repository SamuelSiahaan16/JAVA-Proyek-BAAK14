package com.baak14.javabaak14.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baak14.javabaak14.enums.BookingStatus;
import com.baak14.javabaak14.enums.RuanganStatus;
import com.baak14.javabaak14.model.Ruangan;


@Repository
public interface RuanganRepository extends JpaRepository<Ruangan, Integer> {

    List<Ruangan> findByStatus(RuanganStatus ruanganStatus);

    // Metode pencarian tambahan sesuai kebutuhan

}
