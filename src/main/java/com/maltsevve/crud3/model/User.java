package com.maltsevve.crud3.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Data
@Getter @Setter @NoArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    Region region;
    Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && firstName.equals(user.firstName) && lastName.equals(user.lastName) &&
                region.equals(user.region) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, region, role);
    }
}
