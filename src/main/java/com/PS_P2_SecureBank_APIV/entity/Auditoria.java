package com.PS_P2_SecureBank_APIV.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String accion;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private LocalDateTime fechaEvento;

    @Column(nullable = false)
    private String modulo;

    @Column(nullable = false)
    private Boolean exitoso;

}