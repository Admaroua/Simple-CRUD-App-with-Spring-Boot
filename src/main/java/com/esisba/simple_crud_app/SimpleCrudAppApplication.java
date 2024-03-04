package com.esisba.simple_crud_app;

import com.esisba.simple_crud_app.Enums.Configuration;
import com.esisba.simple_crud_app.Models.MachineVirtuelle;
import com.esisba.simple_crud_app.Models.Serveur;
import com.esisba.simple_crud_app.Models.Utilisateur;
import com.esisba.simple_crud_app.Repositories.MachineVirtuelleRepository;
import com.esisba.simple_crud_app.Repositories.ServeurRepository;
import com.esisba.simple_crud_app.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SimpleCrudAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SimpleCrudAppApplication.class, args);
    }
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private ServeurRepository serveurRepository;
    @Autowired
    private MachineVirtuelleRepository machineVirtuelleRepository;
    @Override
    public void run(String... args) throws Exception {
        Utilisateur user1=new Utilisateur(null,"marwa","mn.adem@gmail.com",null);
        Utilisateur user2=new Utilisateur(null,"adem","m.adem@gmail.com",null);
        Utilisateur user3=new Utilisateur(null,"yacine","adem@gmail.com",null);
        Utilisateur user4=new Utilisateur(null,"younes","adm@gmail.com",null);



        // Save the Utilisateur objects to the database
        utilisateurRepository.save(user1);
        utilisateurRepository.save(user2);
        utilisateurRepository.save(user3);
        utilisateurRepository.save(user4);


        Configuration conf1= new Configuration(100,16,100);

        Serveur serveur1= new Serveur(null,conf1,"dataCenter1",null,null);
        Serveur serveur2= new Serveur(null,conf1,"dataCenter1",null,null);
        Serveur serveur3= new Serveur(null,conf1,"dataCenter1",null,null);
        serveurRepository.save(serveur1);
        serveurRepository.save(serveur2);
        serveurRepository.save(serveur3);



        MachineVirtuelle mv1=new MachineVirtuelle(null,conf1,user1,serveur1,serveur2);
        MachineVirtuelle mv2=new MachineVirtuelle(null,conf1,user2,serveur2,serveur1);
        MachineVirtuelle mv3=new MachineVirtuelle(null,conf1,user3,serveur3,serveur1);
        MachineVirtuelle mv4=new MachineVirtuelle(null,conf1,user3,serveur1,serveur3);
        machineVirtuelleRepository.save(mv1);
        machineVirtuelleRepository.save(mv2);
        machineVirtuelleRepository.save(mv3);
        machineVirtuelleRepository.save(mv4);

        List<Utilisateur> utilisateurs = utilisateurRepository.findByEmailEndingWith("@gmail.com");
        for (Utilisateur utilisateur : utilisateurs) {
            System.out.println(utilisateur);
        }


        List<Object[]> userIdAndMachineCount = utilisateurRepository.findUserIdAndMachineCount();
        for (Object[] result : userIdAndMachineCount) {
            Long userId = (Long) result[0];
            Long machineCount = (Long) result[1];
            System.out.println("Utilisateur ID: " + userId + ", Nombre de machines virtuelles: " + machineCount);
        }









    }
}
