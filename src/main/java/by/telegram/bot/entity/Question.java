package by.telegram.bot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "question")
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "state")
    private String state;
    @Column(name = "ask")
    private String ask;
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<AnswerValue> answer;
}
