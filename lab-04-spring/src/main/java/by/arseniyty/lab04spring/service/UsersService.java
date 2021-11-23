package by.arseniyty.lab04spring.service;

import by.arseniyty.lab04spring.entity.Role;
import by.arseniyty.lab04spring.entity.User;
import by.arseniyty.lab04spring.repository.RolesRepository;
import by.arseniyty.lab04spring.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsersService implements UserDetailsService {

    @Autowired
    UsersRepository repository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User getCurrentAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean saveUser(User user) {
        return saveUser(user, "ROLE_USER");
    }

    public boolean saveUser(User user, String roleName) {
        User userFromDB = repository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        Role usersRole = rolesRepository.findByName(roleName);
        if (usersRole == null) {
            return false;
        }

        user.setRoles(Collections.singleton(usersRole));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        repository.save(user);
        return true;
    }
}
