package com.maltsevve.crud3.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
@Getter @Setter @NoArgsConstructor
public class Post {
    private Long id;
    private String content;
    private Date created;
    private Date updated;
}
