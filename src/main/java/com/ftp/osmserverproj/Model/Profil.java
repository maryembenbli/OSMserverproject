package com.ftp.osmserverproj.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profil")
@Data
public class Profil {


    @Id
    @Column(name="id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="titre", length = 255)
    private String titre;

    @ManyToMany
    @JoinTable(
            name = "profil_group",
            joinColumns = @JoinColumn(name = "profil_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Group> groups;
@OneToMany(mappedBy = "profil", cascade = CascadeType.ALL) //, fetch = FetchType.EAGER
private List<User> users;//List orSet
}

