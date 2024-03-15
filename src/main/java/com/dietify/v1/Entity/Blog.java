package com.dietify.v1.Entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int blogId;

    @ManyToOne
    @JoinColumn(name = "bloggerId")
    private User user;

    String blogType;

    String blogTitle;

    String blogContent;

    String status;

    @Column(name = "publish_date")
    private LocalDateTime publishDate;

    @Override
    public String toString() {
        return "Blog [blogId=" + blogId + ", user=" + user + ", blogTitle=" + blogTitle + ", blogContent=" + blogContent
                + ", status=" + status + ", publishDate=" + publishDate + "]";
    }

    public Blog(int blogId, User user, String blogTitle, String blogContent, String status,
            LocalDateTime publishDate) {
        this.blogId = blogId;
        this.user = user;
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
        this.status = status;
        this.publishDate = publishDate;
    }

    public Blog() {
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public String getBlogType() {
        return blogType;
    }

    public void setBlogType(String blogType) {
        this.blogType = blogType;
    }

}
