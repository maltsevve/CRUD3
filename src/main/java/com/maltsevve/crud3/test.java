package com.maltsevve.crud3;

import com.maltsevve.crud3.model.Post;
import com.maltsevve.crud3.repository.JavaIOPostRepositoryImpl;

public class test {
    public static void main(String[] args) {
        JavaIOPostRepositoryImpl jp = new JavaIOPostRepositoryImpl();

        jp.deleteById(1L);

    }
}
