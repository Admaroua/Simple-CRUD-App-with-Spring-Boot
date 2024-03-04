package com.esisba.simple_crud_app.Services;

import com.esisba.simple_crud_app.Models.MachineVirtuelle;
import com.esisba.simple_crud_app.Models.Serveur;
import com.esisba.simple_crud_app.Repositories.ServeurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServeurService {
    ServeurRepository serveurRepository;
    public List<MachineVirtuelle> getVmsByServeurId(Long idServeur) {
        Serveur serveur = serveurRepository.findById(idServeur).orElse(null);
        if (serveur != null) {
            List<MachineVirtuelle> vms = serveur.getBackup_Mvs();
            vms.addAll(serveur.getPrimary_Mvs());
            return vms;
        } else {
            return null;
        }
    }

}
