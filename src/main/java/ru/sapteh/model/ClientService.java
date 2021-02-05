package ru.sapteh.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "clientservice")
public class ClientService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clientID")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "serviceID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Service service;

    @Column(name = "StartTime")
    private Date startTime;
    @Column(name = "Comment")
    private String comment;

    @Override
    public String toString() {
        return "ClientService{" +
                "id=" + id +
                ", client=" + client +
                ", service=" + service +
                ", startTime=" + startTime +
                ", comment='" + comment + '\'' +
                '}';
    }
}
