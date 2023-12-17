package com.baak14.javabaak14.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baak14.javabaak14.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    List<User> findByNim(String nim);

    List<User> findByNama(String nama);

    List<User> findByEmail(String email);

    List<User> findByRole(String role);

    List<User> findByNoKtp(String noKtp);
 
    User findByUsernameAndPassword(String username, String password);
}
