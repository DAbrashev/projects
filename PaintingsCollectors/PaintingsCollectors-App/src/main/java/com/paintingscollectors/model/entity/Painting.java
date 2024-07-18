package com.paintingscollectors.model.entity;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "paintings")
public class Painting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @ManyToOne
    private Style style;

    @ManyToOne
    private User owner;

    @Column(name = "image_url", nullable = false)
    private String imageURL;

    @Column(name = "is_favourite", nullable = false)
    private boolean isFavourite;


    @Column(nullable = false)
    private long votes;

    @ManyToMany(mappedBy = "favouritePaintings")
    private Set<User> toFavourite;

    public Painting() {
        this.toFavourite = new LinkedHashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }

    public Set<User> getToFavourite() {
        return toFavourite;
    }

    public void setToFavourite(Set<User> toFavourite) {
        this.toFavourite = toFavourite;
    }
}
