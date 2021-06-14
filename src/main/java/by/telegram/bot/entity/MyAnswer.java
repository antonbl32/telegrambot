package by.telegram.bot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "myanswer")
@NoArgsConstructor
public class MyAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
    @ManyToOne
    @JoinColumn(name = "userBot_id", nullable = false)
    private UserBot userBot;
    @ElementCollection
    @CollectionTable(name="myanswers", joinColumns=@JoinColumn(name="answer"))
    @Column(name="answer_id")
    private List<Integer> myAnswers;
}
