package by.telegram.bot.entity;

import by.telegram.bot.en.State;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user")
@NoArgsConstructor
public class UserBot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "state")
    private State status;
    @Column(name = "result")
    private int result;
    @Column(name = "score")
    private int score;
}
