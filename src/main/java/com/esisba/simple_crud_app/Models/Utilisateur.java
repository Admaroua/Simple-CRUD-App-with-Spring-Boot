package com.esisba.simple_crud_app.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    @Column(length = 20,nullable = false)
    private String nom;
    @Column(unique = true,nullable = true)
    private String email;
    @JsonIgnore
    @OneToMany(mappedBy = "utilisateur",fetch = FetchType.EAGER)
    List <MachineVirtuelle> machineVirtuelles;


}
