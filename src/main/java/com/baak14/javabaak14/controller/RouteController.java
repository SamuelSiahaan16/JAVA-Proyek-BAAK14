package com.baak14.javabaak14.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.baak14.javabaak14.model.Order;
import com.baak14.javabaak14.model.Ruangan;
import com.baak14.javabaak14.model.Surat;
import com.baak14.javabaak14.model.User;
import com.baak14.javabaak14.model.BookingRuangan; 

import com.baak14.javabaak14.repository.UserRepository;

@Controller
public class RouteController {
    
    private final RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public RouteController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }
    

    @Autowired
    private UserRepository userRepository;

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
    
    @GetMapping("/admin/surat")
    public ModelAndView surat(Model model, HttpSession session) {
        UserStatus role = (UserStatus) session.getAttribute("role");
        if (role != null && role == UserStatus.admin) {
            try {
                String apiEndpoint = "http://localhost:8080/surat/list";
                Surat[] suratList = restTemplateBuilder.build().getForObject(apiEndpoint, Surat[].class);

                // Tambahkan logika untuk mengambil data pengguna
                Map<Integer, User> userMap = new HashMap<>();
                for (Surat surat : suratList) {
                    int idUser = surat.getIdUser();
                    User userData = getUserById(idUser);
                    userMap.put(idUser, userData);
                }

                model.addAttribute("userMap", userMap);
                model.addAttribute("suratList", suratList);
                return new ModelAndView("admin/surat");
            } catch (Exception e) {
                // Handle exceptions, e.g., log the error and show an error page
                model.addAttribute("error", "Failed to fetch surat data. Please try again.");
                return new ModelAndView("admin/error");
            }
        } else {
            return new ModelAndView("redirect:/logout");
        }
    }



    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    

    




    
    

    // User Route
    @GetMapping("/user")
    public ModelAndView dashboardUser(HttpSession session) {
        UserStatus role = (UserStatus) session.getAttribute("role");
        if (role != null && role == UserStatus.mahasiswa) {
            return new ModelAndView("user/dashboard");
        } else {
            return new ModelAndView("redirect:/logout");
        }
    }
    
    //list surat user
    @GetMapping("/user/request-surat")
    public ModelAndView listSurat(Model model, HttpSession session) {
        UserStatus role = (UserStatus) session.getAttribute("role");
        if (role != null && role == UserStatus.mahasiswa) {
            try {
                String apiEndpoint = "http://localhost:8080/surat/list";
                Surat[] suratList = restTemplateBuilder.build().getForObject(apiEndpoint, Surat[].class);
                model.addAttribute("suratList", suratList);
                return new ModelAndView("user/surat");
            } catch (Exception e) {
                // Handle exceptions, e.g., log the error and show an error page
                model.addAttribute("error", "Failed to fetch surat data. Please try again.");
                return new ModelAndView("user/error");
            }
        } else {
            return new ModelAndView("redirect:/logout");
        }
    }
    
    //add surat user
    @GetMapping("/user/surat/tambah")
    public ModelAndView tambahSurat(Model model,HttpSession session) {
        UserStatus role = (UserStatus) session.getAttribute("role");
        if (role != null && role == UserStatus.mahasiswa) {
        	model.addAttribute("surat", new Surat());
            return new ModelAndView("user/tambah-surat");
        } else {
            return new ModelAndView("redirect:/logout");
        }
    }
    
    //edti surat user
    @GetMapping("/user/surat/edit/{id}")
    public ModelAndView editSurat(@PathVariable int id, Model model, HttpSession session) {
        UserStatus role = (UserStatus) session.getAttribute("role");
        if (role != null && role == UserStatus.mahasiswa) {
            // Panggil API untuk mendapatkan data surat berdasarkan ID
            String apiUrl = "http://localhost:8080/surat/" + id;

            // Menggunakan RestTemplate untuk memanggil API
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Surat> responseEntity = restTemplate.getForEntity(apiUrl, Surat.class);

            // Cek apakah respons dari API sukses dan data surat ditemukan
            if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
                // Data surat dari respons API
                Surat surat = responseEntity.getBody();

                // Kirim data surat ke halaman edit-surat
                model.addAttribute("surat", surat);

                return new ModelAndView("user/edit-surat");
            } else {
                // Jika tidak ditemukan, redirect ke halaman lain atau tampilkan pesan kesalahan
                // Anda dapat menyesuaikan ini sesuai kebutuhan
                return new ModelAndView("redirect:/admin/surat"); // Ganti dengan halaman lain jika perlu
            }
        } else {
            return new ModelAndView("redirect:/logout");
        }
    }
    

	@GetMapping("/user/request-barang")
	public ModelAndView listBarang(Model model, HttpSession session) {
	    UserStatus role = (UserStatus) session.getAttribute("role");
	    Integer userId = (Integer) session.getAttribute("userId"); // Assuming userId is stored in the session as an Integer
	
	    if (role != null && role == UserStatus.mahasiswa && userId != null) {
	        try {
	            String apiEndpoint = "http://localhost:8080/api/orders/user/" + userId;
	            Order[] orderList = restTemplateBuilder.build().getForObject(apiEndpoint, Order[].class);
	            model.addAttribute("orderList", orderList);
	            return new ModelAndView("user/order");
	        } catch (Exception e) {
	            // Handle exceptions, e.g., log the error and show an error page
	            model.addAttribute("error", "Failed to fetch surat data. Please try again.");
	            return new ModelAndView("user/error");
	        }
	    } else {
	        return new ModelAndView("redirect:/logout");
	    }
	}
	 
	@GetMapping("/user/barang/tambah")
	public ModelAndView tambahOrder(Model model, HttpSession session) {
	    UserStatus role = (UserStatus) session.getAttribute("role");
	    if (role != null && role == UserStatus.mahasiswa) {
	        try {
	            // Panggil API Barang untuk mendapatkan daftar barang
	            String apiEndpoint = "http://localhost:8080/barang/list";
	            Barang[] barangList = restTemplateBuilder.build().getForObject(apiEndpoint, Barang[].class);

	            // Set data barang ke model
	            model.addAttribute("barangList", barangList);
	            model.addAttribute("order", new Order()); // Objek Order baru untuk formulir

	            return new ModelAndView("user/tambah-order");
	        } catch (Exception e) {
	            // Handle exceptions, e.g., log the error and show an error page
	            model.addAttribute("error", "Failed to fetch barang data. Please try again.");
	            return new ModelAndView("user/error");
	        }
	    } else {
	        return new ModelAndView("redirect:/logout");
	    }
	}
	
	
	@GetMapping("/user/request-ruangan")
	public ModelAndView listRuangan(Model model, HttpSession session) {
	    UserStatus role = (UserStatus) session.getAttribute("role");
	    Integer userId = (Integer) session.getAttribute("userId"); // Assuming userId is stored in the session as an Integer
	
	    if (role != null && role == UserStatus.mahasiswa && userId != null) {
	        try {
	            String apiEndpoint = "http://localhost:8080/booking-ruangan/user/" + userId;
	            BookingRuangan[] ruanganList = restTemplateBuilder.build().getForObject(apiEndpoint, BookingRuangan[].class);
	            model.addAttribute("ruanganList", ruanganList);
	            return new ModelAndView("user/ruangan");
	        } catch (Exception e) {
	            // Handle exceptions, e.g., log the error and show an error page
	            model.addAttribute("error", "Failed to fetch surat data. Please try again.");
	            return new ModelAndView("user/error");
	        }
	    } else {
	        return new ModelAndView("redirect:/logout");
	    }
	}
	
	@GetMapping("/user/ruangan/tambah")
	public ModelAndView orderRuangan(Model model, HttpSession session) {
	    UserStatus role = (UserStatus) session.getAttribute("role");
	    if (role != null && role == UserStatus.mahasiswa) {
	        try {
	            // Panggil API Barang untuk mendapatkan daftar barang
	            String apiEndpoint = "http://localhost:8080/ruangan/searchByStatus?status=tersedia";
	            Ruangan[] ruanganList = restTemplateBuilder.build().getForObject(apiEndpoint, Ruangan[].class);

	            // Set data barang ke model
	            model.addAttribute("ruanganList", ruanganList);
	            model.addAttribute("ruangan", new BookingRuangan()); // Objek Order baru untuk formulir

	            return new ModelAndView("user/tambah-ruangan");
	        } catch (Exception e) {
	            // Handle exceptions, e.g., log the error and show an error page
	            model.addAttribute("error", "Failed to fetch barang data. Please try again.");
	            return new ModelAndView("user/error");
	        }
	    } else {
	        return new ModelAndView("redirect:/logout");
	    }
	}



    

    // Add more methods for other pages or functionality
}
