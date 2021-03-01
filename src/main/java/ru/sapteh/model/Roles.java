package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    @Column(nullable = false)
    private String role;
    @NonNull
    @OneToOne(mappedBy = "role")
//    @Column(nullable = false)
    private Users user;

    @Override
    public String toString() {
        return getRole();
    }
}
