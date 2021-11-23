package by.arseniyty.lab04spring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(columnDefinition="TEXT")  // To have possibility write more than 255 chars
    private String text;

    @Column(columnDefinition="TEXT")
    private String answer;

    @JsonManagedReference  // to drop recursive json serialisation
    @OneToMany(mappedBy = "question",  // specify that |Comment| is the owning side, |question| is the name of field in |Comment| class
               orphanRemoval = true,  // when we delete smth from comments, and even don't rep.save(...), the corresponding comment also deletes
               cascade=CascadeType.ALL)
    private List<Comment> comments;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
