package com.esisba.simple_crud_app.Models;
import com.esisba.simple_crud_app.Enums.Configuration;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Serveur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServeur;
    @Embedded
    private Configuration configuration;
    @Column
    private String dataCenter;
    @OneToMany(mappedBy = "primary_serveur")
    List <MachineVirtuelle> primary_Mvs;
    @OneToMany(mappedBy = "backup_serveur")
    List <MachineVirtuelle> backup_Mvs;



}
