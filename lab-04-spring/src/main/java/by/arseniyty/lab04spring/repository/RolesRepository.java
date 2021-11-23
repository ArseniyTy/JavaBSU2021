package by.arseniyty.lab04spring.repository;

import by.arseniyty.lab04spring.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
