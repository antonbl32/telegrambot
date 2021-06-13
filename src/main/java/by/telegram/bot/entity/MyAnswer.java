package by.telegram.bot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    @OneToOne(fetch = FetchType.LAZY)
    private Question question;
    @ElementCollection
    @CollectionTable(name="myanswer", joinColumns=@JoinColumn(name="myanswer_id"))
    @Column(name="answer")
    private List<Integer> myAnswers;
}
