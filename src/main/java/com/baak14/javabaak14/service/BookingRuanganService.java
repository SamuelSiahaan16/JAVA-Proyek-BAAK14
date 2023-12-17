package com.baak14.javabaak14.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baak14.javabaak14.model.BookingRuangan;
import com.baak14.javabaak14.repository.BookingRuanganRepository;

@Service
@Transactional
public class BookingRuanganService {

    @Autowired
    private BookingRuanganRepository bookingRuanganRepository;

    public List<BookingRuangan> getDaftarBookingRuangan() {
        return bookingRuanganRepository.findAll();
    }

    public void addBookingRuangan(BookingRuangan bookingRuangan) {
        bookingRuanganRepository.save(bookingRuangan);
    }

    public BookingRuangan getBookingRuanganById(Integer id) {
        return bookingRuanganRepository.findById(id).orElse(null);
    }

    public void deleteBookingRuangan(Integer id) {
        bookingRuanganRepository.deleteById(id);
    }

    public List<BookingRuangan> getBookingRuanganByStatus(String status) {
        return bookingRuanganRepository.findByStatus(status);
    }

    public void updateBookingRuangan(BookingRuangan bookingRuangan) {
        bookingRuanganRepository.save(bookingRuangan);
    }

    public List<BookingRuangan> findConflictingBookings(LocalDate localDate, LocalDate localDate2, int idNamaRuangan) {
        return bookingRuanganRepository.findConflictingBookings(localDate, localDate2, idNamaRuangan);
    }

}
