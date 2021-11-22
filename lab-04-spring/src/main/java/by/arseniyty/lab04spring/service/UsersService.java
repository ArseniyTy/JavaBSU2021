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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsersService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User getCurrentAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = usersRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public Iterable<User> allUsers() {
        return usersRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = usersRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        return true;
    }

    public void saveAdmin(User admin) {
        if (usersRepository.findByUsername(admin.getUsername()) != null) {
            return;
        }
        admin.setRoles(Collections.singleton(new Role(2L, "ROLE_ADMIN")));
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        usersRepository.save(admin);
    }

    public boolean deleteUser(Long userId) {
        if (usersRepository.findById(userId).isPresent()) {
            usersRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
}
