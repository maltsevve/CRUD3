package com.maltsevve.service;

import com.maltsevve.crud3.model.Post;
import com.maltsevve.crud3.service.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {
    Post post = new Post();
    Long userId = 1L;

    @Mock
    PostService postService = mock(PostService.class);

    @Test
    public void saveTest() {
        post.setContent("Test.");
        lenient().when(postService.save(post, userId)).thenReturn(post);
    }

    @Test
    public void updateTest() {
        post.setContent("Test.");
        lenient().when(postService.save(post, userId)).thenReturn(post);
    }

    @Test
    public void getByIdTest() {
        post.setId(1L);
        lenient().when(postService.getById(1L)).thenReturn(post);
    }

    @Test
    public void getAllTest() {
        List<Post> posts = new ArrayList<>();
        post.setId(1L);
        posts.add(post);
        lenient().when(postService.getAll()).thenReturn(posts);
    }

    @Test
    public void deleteByIdTest() {
        postService.deleteById(1L);
        verify(postService, times(1)).deleteById(1L);
    }
}
