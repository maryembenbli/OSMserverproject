package com.ftp.osmserverproj.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.PERSIST) // Cascade the persist operation
    @JoinColumn(name = "email_id")
    private EmailDetails emailDetails;


}