package com.baak14.javabaak14.controller;

import java.util.List;
import java.util.Objects;

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

import com.baak14.javabaak14.enums.BookingStatus;
import com.baak14.javabaak14.model.BookingRuangan;
import com.baak14.javabaak14.model.Order;
import com.baak14.javabaak14.service.BookingRuanganService;

@RestController
@RequestMapping("/booking-ruangan")
@CrossOrigin(origins = "http://localhost:8080")
public class BookingRuanganController {

    private final BookingRuanganService bookingRuanganService;

    @Autowired
    public BookingRuanganController(BookingRuanganService bookingRuanganService) {
        this.bookingRuanganService = bookingRuanganService;
    }

    @GetMapping("/list")
    public List<BookingRuangan> listAllBookingRuangan() {
        return bookingRuanganService.getDaftarBookingRuangan();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingRuangan(@PathVariable Integer id) {
        BookingRuangan bookingRuangan = bookingRuanganService.getBookingRuanganById(id);
        if (bookingRuangan != null) {
            return ResponseEntity.ok(bookingRuangan);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Booking Ruangan not found with ID: " + id + "\"}");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addBookingRuangan(@RequestBody BookingRuangan bookingRuangan) {
        try {
            if (isValidBookingRuangan(bookingRuangan)) {
                bookingRuanganService.addBookingRuangan(bookingRuangan);
                return ResponseEntity.ok("{\"message\": \"Booking Ruangan added successfully.\"}");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"message\": \"Invalid Booking Ruangan data.\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Failed to add Booking Ruangan. Error: " + e.getMessage() + "\"}");
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBookingRuangan(@PathVariable Integer id,
                                                       @RequestBody BookingRuangan bookingRuangan) {
        try {
            if (isValidBookingRuangan(bookingRuangan)) {
                BookingRuangan existingBookingRuangan = bookingRuanganService.getBookingRuanganById(id);
                if (existingBookingRuangan != null) {
                    existingBookingRuangan.setStartDate(bookingRuangan.getStartDate());
                    existingBookingRuangan.setEndDate(bookingRuangan.getEndDate());
                    existingBookingRuangan.setStatus(bookingRuangan.getStatus());
                    existingBookingRuangan.setUser(bookingRuangan.getUser());
                    existingBookingRuangan.setNamaRuangan(bookingRuangan.getNamaRuangan());

                    bookingRuanganService.updateBookingRuangan(existingBookingRuangan);
                    return ResponseEntity.ok("{\"message\": \"Booking Ruangan updated successfully.\"}");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("{\"message\": \"Booking Ruangan not found with ID: " + id + "\"}");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"message\": \"Invalid Booking Ruangan data.\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Failed to update Booking Ruangan. Error: " + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBookingRuangan(@PathVariable Integer id) {
        try {
            bookingRuanganService.deleteBookingRuangan(id);
            return ResponseEntity.ok("{\"message\": \"Booking Ruangan deleted successfully.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Failed to delete Booking Ruangan. Error: " + e.getMessage() + "\"}");
        }
    }

    // Metode pencarian tambahan sesuai kebutuhan

    @GetMapping("/searchByStatus")
    public ResponseEntity<?> searchBookingRuanganByStatus(@RequestParam String status) {
        try {
            BookingStatus bookingStatus = BookingStatus.valueOf(status.toLowerCase());
            List<BookingRuangan> result = bookingRuanganService.getBookingRuanganByStatus(bookingStatus);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            // Handle invalid status string, e.g., return a bad request response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"message\": \"Invalid status value: " + status + "\"}");
        } catch (Exception e) {
            // Handle other exceptions, e.g., log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Internal server error: " + e.getMessage() + "\"}");
        }
    }



    // Metode untuk validasi
    private boolean isValidBookingRuangan(BookingRuangan bookingRuangan) {
    	if (Objects.equals(bookingRuangan, null) || Objects.equals(bookingRuangan.getUser(), null) || Objects.equals(bookingRuangan.getUser().getId(), null)) {
    	    return false; // Invalid data
    	}

        // Check for existing bookings at the same date and time
    	List<BookingRuangan> conflictingBookings = bookingRuanganService.findConflictingBookings(
    	        bookingRuangan.getStartDate(),
    	        bookingRuangan.getEndDate(),
    	        bookingRuangan.getNamaRuangan() != null ? bookingRuangan.getNamaRuangan().getId() : null
    	);

        // If there are conflicting bookings, the new booking is invalid
        return conflictingBookings.isEmpty();
    }
    
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingRuangan>> getBookingByUserId(@PathVariable Integer userId) {
        try {
            List<BookingRuangan> bookingRuanganList = bookingRuanganService.getBookingRuanganByUserId(userId);
            return new ResponseEntity<>(bookingRuanganList, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions, e.g., log the error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
