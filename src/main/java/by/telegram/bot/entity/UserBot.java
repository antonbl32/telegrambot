package by.telegram.bot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.glassfish.grizzly.utils.ArraySet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userBot")
    @ToString.Exclude
    private List<MyAnswer> myAnswer;
    @Column(name = "chatid")
    private Long chatId;
}
