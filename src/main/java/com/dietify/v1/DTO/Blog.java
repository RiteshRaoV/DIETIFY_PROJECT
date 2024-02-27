package com.dietify.v1.DTO;

import java.time.LocalDateTime;

public class Blog {
    public int blogId;
    public String blogContent;
    public String blogTitle;
    public String status;
    public LocalDateTime publishDate;

    
    @Override
    public String toString() {
        return "Blog [blogId=" + blogId + ", blogContent=" + blogContent + ", blogTitle=" + blogTitle + ", status="
                + status + ", publishDate=" + publishDate + "]";
    }
    public Blog() {
    }
    public Blog(int blogId, String blogContent, String blogTitle, String status, LocalDateTime publishDate) {
        this.blogId = blogId;
        this.blogContent = blogContent;
        this.blogTitle = blogTitle;
        this.status = status;
        this.publishDate = publishDate;
    }
    public int getBlogId() {
        return blogId;
    }
    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }
    public String getBlogContent() {
        return blogContent;
    }
    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }
    public String getBlogTitle() {
        return blogTitle;
    }
    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
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

    
}
