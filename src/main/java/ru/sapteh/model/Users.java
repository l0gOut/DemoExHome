package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    @Column(nullable = false)
    private String name;
    @NonNull
    @Column(nullable = false)
    private String login;
    @NonNull
    @Column(nullable = false)
    private String password;
    @NonNull
    @OneToOne
    @JoinColumn(name = "role")
//    @Column(nullable = false)
    private Roles role;

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
