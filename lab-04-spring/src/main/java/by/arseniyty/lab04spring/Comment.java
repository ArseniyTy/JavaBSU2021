package by.arseniyty.lab04spring;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String text;
    private Long likesCount = 0L;
    private Long dislikesCount = 0L;

    @JsonBackReference  // to drop recursive json serialisation
    @ManyToOne
    @JoinColumn(name = "questionId", nullable = false)
    private Question question;
}
