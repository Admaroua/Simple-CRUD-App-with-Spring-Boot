package com.esisba.simple_crud_app.Controllers;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rsu")
public class CustomController {
    @Autowired
    ServeurRepository serveurRepository;
    @Autowired
    MachineVirtuelleRepository machineVirtuelleRepository;
    @Autowired
    UtilisateurRepository utilisateurRepository;
    //GET http://localhost:8081/rsu/serveur/idServeur/vms
    @GetMapping("/serveur/{idServeur}/vms")
    public ResponseEntity<List<MachineVirtuelle>> getVmsByServeurId(@PathVariable Long idServeur) {
        Serveur serveur = serveurRepository.findById(idServeur).orElse(null);
        if (serveur != null) {
            List<MachineVirtuelle> vms = serveur.getBackup_Mvs();
            vms.addAll(serveur.getPrimary_Mvs());
            return ResponseEntity.ok(vms);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //POST http://localhost:8081/rsu/vm/
    @PostMapping("/vm/")
    public MachineVirtuelle addMachineVirtuelle(@RequestBody Map<String, Object> payload){
        MachineVirtuelle vm = new MachineVirtuelle();

        // Set Utilisateur from payload
        Long userId = Long.parseLong((String) payload.get("idUtilisateur"));
        Utilisateur utilisateur =utilisateurRepository.findById(userId).orElse(null);
        vm.setUtilisateur(utilisateur);

        // Set primary Serveur from payload
        Long  idServeur= Long.parseLong((String) payload.get("idServeur"));
        Serveur primaryServeur = serveurRepository.findById(idServeur).orElse(null);
        vm.setPrimary_serveur(primaryServeur);

        // Set backup Serveur from payload
        Long idServeurBackup= Long.parseLong((String) payload.get("idServeurBackup"));
        Serveur BackupServeur = serveurRepository.findById(idServeur).orElse(null);
        vm.setBackup_serveur(BackupServeur);

        // Set configuration from payload
        Map<String, String> configPayload = (Map<String, String>) payload.get("configuration");
        Configuration configuration = new Configuration(
                Integer.parseInt(configPayload.get("cpu")),
                Integer.parseInt(configPayload.get("ram")),
                Integer.parseInt(configPayload.get("disk"))
        );
        vm.setConfiguration(configuration);

        machineVirtuelleRepository.save(vm);
        return vm;
    }
    //PUT http://localhost:8081/rsu/user/{id}
    @PutMapping("/user/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        Utilisateur user = utilisateurRepository.findById(id).orElse(null);
        if (user == null) {
            return "utilisateur non trouvé";
        }
        user.setNom(utilisateur.getNom());
        user.setEmail(utilisateur.getEmail());
        utilisateurRepository.save(user);
        return "User updated successfully";
    }
    @PatchMapping("/user/{id}")
    public String patchUser(@PathVariable Long id, @RequestBody Utilisateur partialUser) {
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



    //DELETE http://localhost:8081/rsu/serveur/{idserveur}/{idvm}:
    public ResponseEntity<String> deleteVm(@PathVariable Long idserveur, @PathVariable Long idvm){
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
