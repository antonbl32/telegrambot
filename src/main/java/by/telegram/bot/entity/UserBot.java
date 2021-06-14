package by.telegram.bot.entity;

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
    @Column(name = "status")
    private String status;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "userBot")
    private List<MyAnswer> myAnswer;
    @Column(name = "chatid")
    private Long chatId;
}
