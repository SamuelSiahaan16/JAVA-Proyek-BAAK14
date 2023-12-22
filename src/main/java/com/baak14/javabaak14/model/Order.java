package com.baak14.javabaak14.model;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_barang")
    private Barang barang;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "total")
    private Double total;

    // Konstruktor
    public Order() {
    }

    // Getter dan setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getBarangId() {
        return (barang != null) ? barang.getId() : 0;
    }

    public Integer getUserId() {
        return (user != null) ? user.getId() : null;
    }

}
