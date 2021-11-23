package by.arseniyty.lab04spring.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Role implements GrantedAuthority {
    @Id
    private Long id;

    // Better with enums
    private String name;  // ROLE_"NAME"

    @Transient  // not stored in db
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
