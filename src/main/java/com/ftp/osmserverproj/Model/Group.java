package com.ftp.osmserverproj.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;
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

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL) //, fetch = FetchType.EAGER

    private List<Catalog> catalogs;//List orSet


    @ManyToMany(mappedBy = "groups")
    private List<Profil> profils;
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, nameG);
//    }
   /* @ManyToMany
    @JoinTable(
            name = "product_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products;*/
}
