package com.baak14.javabaak14.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.baak14.javabaak14.model.BookingRuangan;

public interface BookingRuanganRepository extends JpaRepository<BookingRuangan, Integer> {
    List<BookingRuangan> findByStatus(String status);

    @Query("SELECT b FROM BookingRuangan b " +
            "WHERE b.startDate < :endDate " +
            "AND b.endDate > :startDate " +
            "AND b.namaRuangan.id = :idNamaRuangan")
     List<BookingRuangan> findConflictingBookings(
             @Param("startDate") LocalDate localDate,
             @Param("endDate") LocalDate localDate2,
             @Param("idNamaRuangan") int idNamaRuangan
     );
}
