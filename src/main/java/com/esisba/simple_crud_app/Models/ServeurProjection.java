package com.esisba.simple_crud_app.Models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "pr1", types =Serveur.class)
public interface ServeurProjection {
    public String getIdServeur();
    @Value("#{target.configuration.CPU}")
    public String getCPU();
    @Value("#{target.configuration.RAM}")
    public String getRAM();
    public String getDataCenter();
}
