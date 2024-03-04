package com.esisba.simple_crud_app.Controllers;


import com.esisba.simple_crud_app.Models.MachineVirtuelle;
import com.esisba.simple_crud_app.Models.Utilisateur;

import com.esisba.simple_crud_app.Services.ServeurService;
import com.esisba.simple_crud_app.Services.UtilisateurService;
import com.esisba.simple_crud_app.Services.VmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rsu")
public class CustomController {

    @Autowired
    ServeurService serveurService;
    @Autowired
    VmService vmService;
    @Autowired
    UtilisateurService utilisateurService;
    //GET http://localhost:8081/rsu/serveur/idServeur/vms
    @GetMapping("/serveur/{idServeur}/vms")
    public ResponseEntity<List<MachineVirtuelle>> getVmsByServeurId(@PathVariable Long idServeur) {
        List<MachineVirtuelle> vms = serveurService.getVmsByServeurId(idServeur);
        if (vms != null) {
            return ResponseEntity.ok(vms);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //POST http://localhost:8081/rsu/vm/
    @PostMapping("/vm/")
    public MachineVirtuelle addMachineVirtuelle(@RequestBody Map<String, Object> payload) {
        return vmService.addMachineVirtuelle(payload);
    }
    //PUT http://localhost:8081/rsu/user/{id}
    @PutMapping("/user/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        return utilisateurService.updateUser(id,utilisateur);
    }
    @PatchMapping("/user/{id}")
    public String patchUser(@PathVariable Long id, @RequestBody Utilisateur partialUser) {
        return utilisateurService.patchUser(id,partialUser);
    }



    //DELETE http://localhost:8081/rsu/serveur/{idserveur}/{idvm}:
    @DeleteMapping("/serveur/{idServeur}/{idVm}")
    public ResponseEntity<String> deleteVirMachine(@PathVariable Long idserveur, @PathVariable Long idvm){
        return vmService.deleteVm(idserveur,idvm);
    }


}
