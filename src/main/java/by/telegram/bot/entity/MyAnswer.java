package by.telegram.bot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "myanswer")
@NoArgsConstructor
public class MyAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "question_id", nullable = false, referencedColumnName = "id")
    private Question question;
    @ManyToOne
    @JoinColumn(name = "userBot_id", nullable = false)
    private UserBot userBot;
    @ElementCollection
    @CollectionTable(name="listanswers", joinColumns=@JoinColumn(name="answer"))
    @Column(name="answer_id")
    @ToString.Exclude
    private Set<Integer> answers;

}
