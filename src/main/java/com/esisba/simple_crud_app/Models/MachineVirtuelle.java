package com.esisba.simple_crud_app.Models;

import com.esisba.simple_crud_app.Enums.Configuration;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@ToString(exclude = {"utilisateur", "primary_serveur", "backup_serveur"})
public class MachineVirtuelle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMv;
    @Embedded
    private Configuration configuration;
    @ManyToOne
    @JoinColumn(name= "id_user")
    private Utilisateur utilisateur;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "primary_serveur")
    private Serveur primary_serveur;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "backup_serveur")
    private Serveur backup_serveur;


}
