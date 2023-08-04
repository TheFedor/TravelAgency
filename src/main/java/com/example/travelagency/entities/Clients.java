package com.example.travelagency.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Clients {

    @Id
    @Column(name = "client_login", nullable = false, unique = true)
    @Size(min = 5, max = 25)
    private String clientLogin;

    @Column(name = "client_password", nullable = false)
    @Size(min = 8, max = 18)
    private String clientPassword;

    @Column(name = "last_name", nullable = false)
    @Size(min = 2, max = 36)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    @Size(min = 1, max = 49)
    private String firstName;

    @Column(name = "patronymic")
    @Size(max = 100)
    private String patronymic;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @Column(name = "phone_number", nullable = false)
    @Size(min = 11, max = 12)
    private String phoneNumber;

    @Column(name = "passport_series")
    @Size(min = 4, max = 4)
    private String passportSeries;

    @Column(name = "passport_number")
    @Size(min = 6, max = 6)
    private String passportNumber;

    @Override
    public String toString() {
        return "Clients{" +
                "clientLogin='" + clientLogin + '\'' +
                ", clientPassword='" + clientPassword + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", age=" + age +
                ", emailAddress='" + emailAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", passportSeries='" + passportSeries + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                '}';
    }
}
