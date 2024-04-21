package com.ftp.osmserverproj.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="\"group\"")
@Data
public class Group {
    @Id
    @Column(name="id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="nameG", length = 255)
    private String nameG;

    @ManyToMany(mappedBy = "groups")
    private Set<Profil> profils;

    @ManyToMany
    @JoinTable(
            name = "product_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products;
}
