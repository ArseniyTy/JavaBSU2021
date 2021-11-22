package by.arseniyty.lab04spring.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="userEntity")  // postgresql can't create table with name |user|
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=2, message = "More than 2 symbols")
    private String username;

    @Size(min=2, message = "More than 2 symbols")
    private String password;

    @Transient
    private String passwordConfirm;  // double check just in form, won't be in db

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @JsonManagedReference
    @OneToMany(mappedBy = "user",
               orphanRemoval = true,
               cascade=CascadeType.ALL)
    private List<Comment> comments;

    @JsonManagedReference
    @OneToMany(mappedBy = "user",
            orphanRemoval = true,
            cascade=CascadeType.ALL)
    private List<Question> questions;

    @JsonManagedReference
    @OneToMany(mappedBy = "user",
            orphanRemoval = true,
            cascade=CascadeType.ALL)
    private List<Reaction> reactions;

//    public User(String username, String password, RO) {
//        setUsername(username);
//        setPassword(password);
//    }

//    @Override
//    public String getUsername() {
//        return username;
//    }

//    @Override
//    public String getPassword() {
//        return password;
//    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
}
