package com.esisba.simple_crud_app.Repositories;

import com.esisba.simple_crud_app.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    List <Utilisateur> findByEmailEndingWith(String domain); //Query Derivation
    @Query("SELECT u.idUser, count(mv) from Utilisateur u LEFT JOIN u.machineVirtuelles mv GROUP BY u.idUser")
    List <Object[]> findUserIdAndMachineCount();
}
