package com.esisba.simple_crud_app.Services;

import com.esisba.simple_crud_app.Enums.Configuration;
import com.esisba.simple_crud_app.Models.MachineVirtuelle;
import com.esisba.simple_crud_app.Models.Serveur;
import com.esisba.simple_crud_app.Models.Utilisateur;
import com.esisba.simple_crud_app.Repositories.MachineVirtuelleRepository;
import com.esisba.simple_crud_app.Repositories.ServeurRepository;
import com.esisba.simple_crud_app.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Service
public class VmService {
    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    private ServeurRepository serveurRepository;

    @Autowired
    private MachineVirtuelleRepository machineVirtuelleRepository;
    public MachineVirtuelle addMachineVirtuelle(Map<String, Object> payload) {
        // Extract payload data
        Long userId = Long.parseLong((String) payload.get("idUtilisateur"));
        Long primaryServeurId = Long.parseLong((String) payload.get("idServeur"));
        Long backupServeurId = Long.parseLong((String) payload.get("idServeurBackup"));
        Map<String, String> configPayload = (Map<String, String>) payload.get("configuration");

        // Fetch related entities
        Utilisateur utilisateur = utilisateurRepository.findById(userId).orElse(null);
        Serveur primaryServeur = serveurRepository.findById(primaryServeurId).orElse(null);
        Serveur backupServeur = serveurRepository.findById(backupServeurId).orElse(null);

        // Create and save MachineVirtuelle
        MachineVirtuelle vm = new MachineVirtuelle();
        vm.setUtilisateur(utilisateur);
        vm.setPrimary_serveur(primaryServeur);
        vm.setBackup_serveur(backupServeur);
        Configuration configuration = new Configuration(
                Integer.parseInt(configPayload.get("cpu")),
                Integer.parseInt(configPayload.get("ram")),
                Integer.parseInt(configPayload.get("disk"))
        );
        vm.setConfiguration(configuration);
        machineVirtuelleRepository.save(vm);

        return vm;
    }

    public ResponseEntity<String> deleteVm(Long idserveur, Long idvm){

        Serveur serveur= serveurRepository.findById(idserveur).orElse(null);
        if(serveur == null){
            return new ResponseEntity<>("serveur non trouvé", HttpStatus.NOT_FOUND);
        }
        MachineVirtuelle vm= machineVirtuelleRepository.findById(idvm).orElse(null);
        if(vm==null){
            return new ResponseEntity<>("vm non trouvée", HttpStatus.NOT_FOUND);
        }
        if(!serveur.getPrimary_Mvs().contains(vm)){
            return new ResponseEntity<>("Cette machine virtuelle n'est pas déployée sur ce serveur", HttpStatus.BAD_REQUEST);
        }
        serveur.getPrimary_Mvs().remove(vm);
        return new ResponseEntity<>("La machine virtuelle a été supprimée du serveur avec succès", HttpStatus.OK);
    }
}
