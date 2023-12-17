package com.baak14.javabaak14.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
 
import com.baak14.javabaak14.enums.UserStatus;
import com.baak14.javabaak14.model.Barang;
import com.baak14.javabaak14.model.Ruangan; 

@Controller
public class RouteController {
    
    private final RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public RouteController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    // Admin Route
    @GetMapping("/admin")
    public ModelAndView dashboard(HttpSession session) {
        UserStatus role = (UserStatus) session.getAttribute("role");
        if (role != null && role == UserStatus.admin) {
            return new ModelAndView("admin/dashboard");
        } else {
            return new ModelAndView("redirect:/logout");
        }
    }
    
    @GetMapping("/admin/ruangan")
    public ModelAndView ruangan(Model model, HttpSession session) {
        UserStatus role = (UserStatus) session.getAttribute("role");
        if (role != null && role == UserStatus.admin) {
            String apiEndpoint = "http://localhost:8080/ruangan/list";
             
            Ruangan[] ruanganList = restTemplateBuilder.build().getForObject(apiEndpoint, Ruangan[].class);

            model.addAttribute("ruanganList", ruanganList);
            return new ModelAndView("admin/ruangan");
        } else {
            return new ModelAndView("redirect:/logout");
        }
    }
    
    @GetMapping("/admin/ruangan/tambah")
    public ModelAndView tambahRuanganPage(Model model,HttpSession session) {
        UserStatus role = (UserStatus) session.getAttribute("role");
        if (role != null && role == UserStatus.admin) {
        	model.addAttribute("ruangan", new Ruangan());
            return new ModelAndView("admin/tambah-ruangan");
        } else {
            return new ModelAndView("redirect:/logout");
        }
    }
    
    
    @GetMapping("/admin/barang")
    public ModelAndView barang(Model model, HttpSession session) {
        UserStatus role = (UserStatus) session.getAttribute("role");
        if (role != null && role == UserStatus.admin) {
            try {
                String apiEndpoint = "http://localhost:8080/barang/list";
                Barang[] barangList = restTemplateBuilder.build().getForObject(apiEndpoint, Barang[].class);
                model.addAttribute("barangList", barangList);
                return new ModelAndView("admin/barang");
            } catch (Exception e) {
                // Handle exceptions, e.g., log the error and show an error page
                model.addAttribute("error", "Failed to fetch barang data. Please try again.");
                return new ModelAndView("admin/error");
            }
        } else {
            return new ModelAndView("redirect:/logout");
        }
    }

    
    @GetMapping("/admin/barang/tambah")
    public ModelAndView tambahBarang(Model model,HttpSession session) {
        UserStatus role = (UserStatus) session.getAttribute("role");
        if (role != null && role == UserStatus.admin) {
        	model.addAttribute("barang", new Barang());
            return new ModelAndView("admin/tambah-barang");
        } else {
            return new ModelAndView("redirect:/logout");
        }
    }
    
    @GetMapping("/admin/barang/edit/{id}")
    public ModelAndView editBarang(@PathVariable int id, Model model, HttpSession session) {
        UserStatus role = (UserStatus) session.getAttribute("role");
        if (role != null && role == UserStatus.admin) {
            // Panggil API untuk mendapatkan data barang berdasarkan ID
            String apiUrl = "http://localhost:8080/barang/" + id;
            
            // Menggunakan RestTemplate untuk memanggil API
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Barang> responseEntity = restTemplate.getForEntity(apiUrl, Barang.class);

            // Cek apakah respons dari API sukses dan data barang ditemukan
            if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
                // Data barang dari respons API
                Barang barang = responseEntity.getBody();

                // Kirim data barang ke halaman edit-barang
                model.addAttribute("barang", barang);

                return new ModelAndView("admin/edit-barang");
            } else {
                // Jika tidak ditemukan, redirect ke halaman lain atau tampilkan pesan kesalahan
                // Anda dapat menyesuaikan ini sesuai kebutuhan
                return new ModelAndView("redirect:/admin/barang"); // Ganti dengan halaman lain jika perlu
            }
        } else {
            return new ModelAndView("redirect:/logout");
        }
    }

    




    
    

    // User Route
    @GetMapping("/user")
    public String contact() { 
        return "user/dashboard";
    }

    // Add more methods for other pages or functionality
}
