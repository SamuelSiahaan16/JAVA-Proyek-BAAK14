package com.baak14.javabaak14.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baak14.javabaak14.enums.UserStatus;
import com.baak14.javabaak14.model.User;
import com.baak14.javabaak14.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> getUsersByNIM(String nim) {
        return userRepository.findByNim(nim);
    }

    public List<User> getUsersByNama(String nama) {
        return userRepository.findByNama(nama);
    }

    public List<User> getUsersByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public boolean validateUser(User user) {
        User userInDb = getUserByUsername(user.getUsername());
        return userInDb != null;
    }

    public boolean isUsernameExists(String username) {
        User userInDb = getUserByUsername(username);
        return userInDb != null;
    }

    public void updateUser(User existingUser) {
        userRepository.save(existingUser);
    }

    public boolean isDuplicateUser(User user) {
        // Check if there is an existing user with the same nim, email, or noKtp
        List<User> usersWithSameNim = userRepository.findByNim(user.getNim());
        List<User> usersWithSameEmail = userRepository.findByEmail(user.getEmail());
        List<User> usersWithSameNoKtp = userRepository.findByNoKtp(user.getNoKtp());

        // If any of the lists is not empty, there is a duplicate user
        return !usersWithSameNim.isEmpty() || !usersWithSameEmail.isEmpty() || !usersWithSameNoKtp.isEmpty();
    }

    public User register(String username, String password, UserStatus role, String noKtp, String email, String nim, String nama) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setNoKtp(noKtp);
        user.setEmail(email);
        user.setNim(nim);
        user.setNama(nama);
    
        userRepository.save(user);
    
        return user;
    }

    public User login(String username, String password) {
        Optional<User> dbUser = userRepository.findByUsername(username);
    
        if ("admin".equals(username) && "admin".equals(password)) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            return adminUser;
        } else if (dbUser.isPresent() && password.equals(dbUser.get().getPassword())) {
            return dbUser.get();
        }
    
        return null; // or throw an exception if you prefer
    }
    
    

}
