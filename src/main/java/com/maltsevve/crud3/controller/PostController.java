package com.maltsevve.crud3.controller;

import com.maltsevve.crud3.model.Post;
import com.maltsevve.crud3.service.PostService;

import java.util.List;

public class PostController {
    PostService postService = new PostService();

    public Post save(Post post, Long userId) {
        return postService.save(post, userId);
    }

    public Post update(Post post, Long userId) {
        return postService.update(post, userId);
    }

    public Post getById(Long aLong) {
        return postService.getById(aLong);
    }

    public List<Post> getAll() {
        return postService.getAll();
    }

    public void deleteById(Long aLong) {
        postService.deleteById(aLong);
    }
}
