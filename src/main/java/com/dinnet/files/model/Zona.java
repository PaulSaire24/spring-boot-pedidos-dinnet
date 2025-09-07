package com.dinnet.files.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "zonas")
public class Zona {
    @Id
    private String id;

    @Column(name = "soporte_refrigeracion")
    private Boolean soporteRefrigeracion;
}
