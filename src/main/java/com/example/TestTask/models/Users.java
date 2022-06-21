package com.example.TestTask.models;

import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @XmlTransient
    private List<Roles> rolesList;

    @Override
    public String toString() {
        return "Users{" +
                "login=" + login +
                ", first_name=" + firstName +
                ", password='" + password + '\'' +
                '}';
    }

    public Users(String login, String firstName, String password) {
        this.login = login;
        this.firstName = firstName;
        this.password = password;
    }
}
