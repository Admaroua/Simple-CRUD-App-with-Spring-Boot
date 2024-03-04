package com.esisba.simple_crud_app.Repositories;

import com.esisba.simple_crud_app.Models.Serveur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServeurRepository extends JpaRepository<Serveur, Long> {
}
