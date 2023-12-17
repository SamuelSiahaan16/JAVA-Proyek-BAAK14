package com.baak14.javabaak14.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baak14.javabaak14.enums.UserStatus;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String nim;

    @Column(name = "nama_lengkap")
    private String nama;

    private String email;

    @Column(name = "username")
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserStatus role;

    @Column(name = "no_ktp")
    private String noKtp;

    public User() {
    }

    public User(String nim, String nama, String email, String username, String password, UserStatus role, String noKtp) {
        this.nim = nim;
        this.nama = nama;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.noKtp = noKtp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getRole() {
        return role;
    }

    public void setRole(UserStatus role) {
        this.role = role;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    public void setRole(String string) {
    }
}
