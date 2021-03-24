package com.maltsevve.crud3.model.builders.post;

public class ActualPostBuilder extends PostBuilder{
    String content;

    public ActualPostBuilder(String content) {
        this.content = content;
    }

    @Override
    public void buildContent() {
        post.setContent(content);
    }
}
