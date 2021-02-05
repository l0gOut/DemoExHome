package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name="client")
public class Client {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "GenderCode")
    private Gender gender;
    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "Patronymic")
    private String patronymic;
    @Column(name = "Birthday")
    private Date birthday;
    @Column(name = "Phone")
    private String phoneNumber;
    @Column(name = "Email")
    private String email;
    @Column(name = "RegistrationDate")
    private Date registrationDate;
    @Column(name = "PhotoPath")
    private String photoPath;
    @OneToMany(mappedBy = "client")
    private Set<ClientService> clientService;


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", gender=" + gender +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthday=" + birthday +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", registrationDate=" + registrationDate +
                ", photoPath='" + photoPath + '\'' +
                '}';
    }
}
