package by.telegram.bot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "answers")
@NoArgsConstructor
public class AnswerValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "Value")
    private String value;
    @ManyToOne
    private Question question;
}
