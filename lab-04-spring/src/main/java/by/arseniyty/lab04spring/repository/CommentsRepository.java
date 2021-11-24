package by.arseniyty.lab04spring.repository;

import by.arseniyty.lab04spring.entity.Comment;
import by.arseniyty.lab04spring.entity.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends CrudRepository<Comment, Long> {
    Iterable<Comment> findAllByQuestion(Question question);

//    int getCommentByIdAndReactions

//    @Query("SELECT U.name FROM User U WHERE LOWER(U.name) LIKE LOWER(?1)")
//    List<String> findByName(String matchPhrase);
}
