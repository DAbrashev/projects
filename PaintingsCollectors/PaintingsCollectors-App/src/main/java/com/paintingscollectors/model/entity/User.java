package com.paintingscollectors.model.entity;

import org.thymeleaf.standard.expression.Each;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true,nullable = false)
    private String email;

    @OneToMany(mappedBy = "owner")
    private List<Painting> paintings;

    @ManyToMany
    @JoinTable(
            name = "users_favourite_paintings",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "painting_id", referencedColumnName = "id")

    )
    private List<Painting> favouritePaintings;

    @ManyToMany
    @JoinTable(
            name = "users_rated_paintings",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "painting_id", referencedColumnName = "id")

    )
    private List<Painting> ratedPaintings;

    public User() {
        this.paintings = new ArrayList<>();
        this.favouritePaintings = new ArrayList<>();
        this.ratedPaintings = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Painting> getPaintings() {
        return paintings;
    }

    public void setPaintings(List<Painting> paintings) {
        this.paintings = paintings;
    }

    public List<Painting> getFavoritePaintings() {
        return favouritePaintings;
    }

    public void setFavoritePaintings(List<Painting> favoritePaintings) {
        this.favouritePaintings = favoritePaintings;
    }

    public List<Painting> getRatedPaintings() {
        return ratedPaintings;
    }

    public void setRatedPaintings(List<Painting> ratedPaintings) {
        this.ratedPaintings = ratedPaintings;
    }
}
