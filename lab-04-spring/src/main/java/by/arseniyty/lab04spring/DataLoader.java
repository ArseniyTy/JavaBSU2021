package by.arseniyty.lab04spring;

import by.arseniyty.lab04spring.entity.Role;
import by.arseniyty.lab04spring.entity.User;
import by.arseniyty.lab04spring.repository.RolesRepository;
import by.arseniyty.lab04spring.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private UsersService usersService;

    @Autowired
    private RolesRepository rolesRepository;

    // Preloads data to db
    public void run(ApplicationArguments args) {
        rolesRepository.save(new Role(1L, "ROLE_USER"));
        rolesRepository.save(new Role(2L, "ROLE_ADMIN"));
        usersService.saveUser(new User("admin", "ppp"), "ROLE_ADMIN");
    }
}