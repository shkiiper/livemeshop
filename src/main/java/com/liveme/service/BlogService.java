package com.liveme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liveme.entity.Blog;
import com.liveme.repository.BlogRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public Blog updateBlog(Long id, Blog updatedBlog) {
        return blogRepository.findById(id).map(blog -> {
            blog.setTitle(updatedBlog.getTitle());
            blog.setText(updatedBlog.getText());
            blog.setImg(updatedBlog.getImg());
            return blogRepository.save(blog);
        }).orElse(null);
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
