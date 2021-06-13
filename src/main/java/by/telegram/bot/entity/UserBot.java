package by.telegram.bot.entity;

import by.telegram.bot.en.State;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    private State status=State.NONE;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "id")
    private List<MyAnswer> myAnswer;
    @Column(name = "chatid")
    private Long chatId;
}
