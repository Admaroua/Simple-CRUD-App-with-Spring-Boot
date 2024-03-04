package com.esisba.simple_crud_app.Services;

import com.esisba.simple_crud_app.Models.Utilisateur;
import com.esisba.simple_crud_app.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UtilisateurService {
    @Autowired
    UtilisateurRepository utilisateurRepository;
    public String updateUser(Long id,Utilisateur utilisateur) {
        Utilisateur user = utilisateurRepository.findById(id).orElse(null);
        if (user == null) {
            return "utilisateur non trouvé";
        }
        user.setNom(utilisateur.getNom());
        user.setEmail(utilisateur.getEmail());
        utilisateurRepository.save(user);
        return "User updated successfully";
    }

    public String patchUser(Long id, Utilisateur partialUser) {
        Utilisateur existingUser = utilisateurRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return "User not found";
        }

        if (partialUser.getNom() != null) {
            existingUser.setNom(partialUser.getNom());
        }

        utilisateurRepository.save(existingUser);

        return "User updated successfully";
    }
}
