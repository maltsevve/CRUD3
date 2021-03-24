package com.maltsevve.crud3.model.builders.post;

import com.maltsevve.crud3.model.Post;

public class PostDirector {
    PostBuilder postBuilder;

    public void setPostBuilder(PostBuilder postBuilder) {
        this.postBuilder = postBuilder;
    }

    public Post buildPost() {
        postBuilder.buildPost();
        postBuilder.buildContent();
        return postBuilder.getPost();
    }
}
