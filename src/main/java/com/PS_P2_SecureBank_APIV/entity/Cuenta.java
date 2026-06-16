package com.PS_P2_SecureBank_APIV.entity;

import com.PS_P2_SecureBank_APIV.enums.EstadoCuenta;
import com.PS_P2_SecureBank_APIV.enums.TipoCuenta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cuentas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroCuenta;

    @Column(unique = true)
    private String numeroTarjetaDebito;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoCuenta estado;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal saldo;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}