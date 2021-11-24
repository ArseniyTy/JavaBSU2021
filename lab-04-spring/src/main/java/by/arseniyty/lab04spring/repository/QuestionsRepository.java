package by.arseniyty.lab04spring.repository;

import by.arseniyty.lab04spring.entity.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepository extends CrudRepository<Question, Long> {
    Iterable<Question> findAllByTextContainsIgnoreCase(String value);
}
