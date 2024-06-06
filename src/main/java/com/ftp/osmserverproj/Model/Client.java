package com.ftp.osmserverproj.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
@Data
public class Client {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="firstname", length = 255)
    private String firstname;
    @Column(name="lastname", length = 255)
    private String lastname;
    @Column(name="NCIN", length = 8)
    private String ncin;
    @Column(name="gender", length = 255)
    private String gender;
    @Column(name="datebirth")
    private Date datebirth;
    @Column(name="address", length = 255)
    private String address;
    @Column(name="city", length = 255)
    private String city;
    @Column(name="region", length = 255)
    private String region;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)

    private List<Contrat> contrats; //use list or set ?

}
