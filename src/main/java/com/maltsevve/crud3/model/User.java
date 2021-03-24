package com.maltsevve.crud3.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter @Setter @NoArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    Region region;
}
