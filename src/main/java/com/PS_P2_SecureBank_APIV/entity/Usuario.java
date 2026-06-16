package com.PS_P2_SecureBank_APIV.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 8,unique = true)
    private String dni;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false,unique = true)
    private String correo;

    @Column(nullable = false)
    private String celular;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private Boolean activo;

    private LocalDateTime fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @OneToMany(mappedBy = "usuario")
    private List<Cuenta> cuentas;
}