package by.arseniyty.lab04spring;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition="TEXT")
    private String text;

    @Column(columnDefinition="TEXT")
    private String answer;

    @JsonManagedReference  // to drop recursive json serialisation
    // With |mappedBy| we specify that |Comment| is the owning side
    @OneToMany(mappedBy = "question",  // |question| is the name of field in |Comment| class
               orphanRemoval = true,
               cascade=CascadeType.ALL)
    private List<Comment> comments;
}
