package com.dietify.v1.Entity;

import org.bson.types.ObjectId;

import jakarta.persistence.*;

@Entity
@Table(name = "favourite")
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "favourite_id", nullable = false)
    private String favouriteId; // Using String to represent ObjectId

    @Column(name = "type", nullable = false)
    private String type; // Assuming it's a String, update accordingly

    @Column(name = "title", nullable = false)
    private String title;

    // Constructors

    public Favourite() {
    }

    public Favourite(User user, ObjectId favouriteId, String type, String title) {
        this.user = user;
        this.favouriteId = favouriteId.toString(); // Convert ObjectId to String
        this.type = type;
        this.title = title;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFavouriteId() {
        return favouriteId;
    }

    public void setFavouriteId(ObjectId favouriteId) {
        this.favouriteId = favouriteId.toString();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return "Favourite{" +
                "id=" + id +
                ", user=" + user +
                ", favouriteId='" + favouriteId + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
