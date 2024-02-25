package com.dietify.v1.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.dietify.v1.Entity.Blog;
import com.dietify.v1.Entity.User;
import com.dietify.v1.Repository.BlogRepo;
import com.dietify.v1.Repository.UserRepo;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogRepo repo;

    @Autowired
    private UserRepo userRepository;

    @GetMapping("/allBlogs")
    public String allBlogs(Model model) {
        List<Blog> blogs = repo.findAll();
        model.addAttribute("blogs", blogs);
        return "allBlogs";
    }

    @PostMapping("/changeStatus")
    public String changeBlogStatus(@RequestParam("blogId") int blogId, @RequestParam("status") String status) {
        Optional<Blog> optionalBlog = repo.findById(blogId);
        if (optionalBlog.isPresent()) {
            Blog blog = optionalBlog.get();
            blog.setStatus(status);
            repo.save(blog);
            return "redirect:/blogs/allBlogs";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/approvedBlogs")
    public String allApprovedBlogs(Model model) {
        List<Blog> approvedBlogs = repo.findByStatus("Approved");
        model.addAttribute("approvedBlogs", approvedBlogs);
        return "blogs";
    }

    @GetMapping("/myBlogs")
    public String myBlogs(Model model, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail);
        if (user != null) {
            List<Blog> myBlogs = repo.findByUser_Id(user.getId());
            model.addAttribute("myBlogs", myBlogs);
            return "blogger-home";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/addBlogs")
    public String addBlogs() {
        return "add-blogs";
    }

    @PostMapping("/save")
    public String saveBlog(@ModelAttribute Blog blog, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail);

            if (user != null) {
                blog.setUser(user);
                blog.setStatus("Pending");
                repo.save(blog);
            }
        }

        return "redirect:/blogs/myBlogs";
    }

}
