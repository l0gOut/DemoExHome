package ru.sapteh.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="client")
public class Client {
    @Id
    @GeneratedValue
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    private Gender gender;
    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "Patronymic")
    private String patronymic;
    @Column(name = "Birthday")
    private Date birthday;
    private String phoneNumber;
    private String email;
    @Column(name = "RegistrationDate")
    private Date registrationDate;
    private Date dateLastLog;
    private int valueLog;
    private String tags;
}
