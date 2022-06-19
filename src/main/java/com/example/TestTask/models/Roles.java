package com.example.TestTask.models;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_roles")
    private long id;

    @Column(name = "name_role")
    private String nameRole;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "login")
    @XmlTransient
    private Users user;

    @Override
    public String toString() {
        return "Roles{" +
                "id=" + id +
                ", name_role=" + nameRole +
                ", user='" + user + '\'' +
                '}';
    }

    public Roles(long id, String nameRole) {
        this.id = id;
        this.nameRole = nameRole;
    }
}
