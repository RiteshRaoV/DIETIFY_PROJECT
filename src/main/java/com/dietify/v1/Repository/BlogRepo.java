package com.dietify.v1.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dietify.v1.Entity.Blog;

@Repository
public interface BlogRepo extends JpaRepository<Blog, Integer> {
    List<Blog> findByUser_Id(int userId);

    List<Blog> findByStatus(String status);

}
