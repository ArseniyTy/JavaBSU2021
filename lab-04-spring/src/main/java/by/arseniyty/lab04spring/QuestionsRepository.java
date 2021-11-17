package by.arseniyty.lab04spring;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepository extends CrudRepository<Question, Long> {
    Iterable<Question> findAllByTextContainsIgnoreCase(String value);
}
