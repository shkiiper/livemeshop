package com.liveme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.liveme.entity.Blog;
import com.liveme.service.BlogService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("blog")
public class BlogController {
    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public List<Blog> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
        Optional<Blog> blog = blogService.getBlogById(id);
        return blog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        Blog createdBlog = blogService.createBlog(blog);
        return ResponseEntity.ok(createdBlog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long id, @RequestBody Blog updatedBlog) {
        Blog updated = blogService.updateBlog(id, updatedBlog);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }
}
