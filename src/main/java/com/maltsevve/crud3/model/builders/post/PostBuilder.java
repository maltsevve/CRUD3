package com.maltsevve.crud3.model.builders.post;

import com.maltsevve.crud3.model.Post;

public abstract class PostBuilder {
    Post post;

    public void buildPost() {
        post = new Post();
    }

    public void buildContent() {

    }

    public Post getPost() {
        return post;
    }
}
