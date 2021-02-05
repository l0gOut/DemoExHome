package ru.sapteh.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="gender")
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private char code;
    @Column(name = "Name")
    private String name;

    @Override
    public String toString() {
        return "Gender{" +
                "Code=" + code +
                ", Name='" + name + '\'' +
                '}';
    }
}
