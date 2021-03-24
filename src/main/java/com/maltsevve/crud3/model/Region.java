package com.maltsevve.crud3.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter @Setter @NoArgsConstructor
public class Region {
    private Long id;
    private String name;
}
