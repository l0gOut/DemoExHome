package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @NonNull
    @Column(name = "Title")
    private String title;
    @NonNull
    @Column(name = "Color")
    private char color;

    @ManyToMany
    @JoinTable(
            name = "tagofclient",
            joinColumns = @JoinColumn(name = "TagID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ClientID", referencedColumnName = "ID")
    )
    private Set<Client> clients;

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", color=" + color +
                '}';
    }
}
