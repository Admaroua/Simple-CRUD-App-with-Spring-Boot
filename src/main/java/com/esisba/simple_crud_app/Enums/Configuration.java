package com.esisba.simple_crud_app.Enums;

import jakarta.persistence.*;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
@Embeddable
public class Configuration {
    private Integer CPU;
    private Integer RAM;
    private Integer DisckSize;
}
