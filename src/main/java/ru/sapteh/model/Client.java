package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name="client")
public class Client {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "GenderCode")
    @NonNull
    private Gender gender;
    @Column(name = "FirstName")
    @NonNull
    private String firstName;
    @Column(name = "LastName")
    @NonNull
    private String lastName;
    @Column(name = "Patronymic")
    @NonNull
    private String patronymic;
    @Column(name = "Birthday")
    @NonNull
    private Date birthday;
    @Column(name = "Phone")
    @NonNull
    private String phoneNumber;
    @Column(name = "Email")
    @NonNull
    private String email;
    @Column(name = "RegistrationDate")
    @NonNull
    private Date registrationDate;
    @Column(name = "PhotoPath")
    private String photoPath;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClientService> clientService;
    @ManyToMany(mappedBy = "clients", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Tag> tags;


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
