package by.arseniyty.lab04spring.repository;

import by.arseniyty.lab04spring.entity.Comment;
import by.arseniyty.lab04spring.entity.Reaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionsRepository extends CrudRepository<Reaction, Long> {
    Iterable<Reaction> findAllByCommentId(Long commentId);
    Reaction findByUserIdAndCommentId(Long userId, Long commentId);
}
