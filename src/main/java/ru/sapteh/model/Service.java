package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "Title")
    private String title;
    @Column(name = "Cost")
    private double cost;
    @Column(name = "DurationInSeconds")
    private int durationInSeconds;
    @Column(name = "Discount")
    private double discount;
    @Column(name = "Description")
    private String description;
    @Column(name = "MainImagePath")
    private String mainImagePath;

    @OneToMany(mappedBy = "service")
    private Set<ClientService> clientServiceEntities;

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                ", durationInSeconds=" + durationInSeconds +
                ", discount=" + discount +
                ", description='" + description + '\'' +
                ", mainImagePath='" + mainImagePath + '\'' +
                '}';
    }
}
