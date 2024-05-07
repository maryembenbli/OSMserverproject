package com.ftp.osmserverproj.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "catalogue")
@Data
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "catalog_Name", nullable = false)
    private String catalogName;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)

    private Group group;


    @OneToMany(mappedBy = "catalog", fetch = FetchType.EAGER)

    private List<Product> products;

    @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL)

    private List<Contrat> contrats;//Set or List ?
    @Override
    public int hashCode() {
        return Objects.hash(id, catalogName);
    }
}