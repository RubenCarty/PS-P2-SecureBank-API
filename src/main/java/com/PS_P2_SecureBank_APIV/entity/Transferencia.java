package com.PS_P2_SecureBank_APIV.entity;

import com.PS_P2_SecureBank_APIV.enums.EstadoTransferencia;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transferencias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numeroOperacion;

    @ManyToOne
    @JoinColumn(name = "cuenta_origen_id")
    private Cuenta cuentaOrigen;

    @ManyToOne
    @JoinColumn(name = "cuenta_destino_id")
    private Cuenta cuentaDestino;

    private BigDecimal monto;

    private String descripcion;

    private LocalDateTime fechaTransferencia;

    @Enumerated(EnumType.STRING)
    private EstadoTransferencia estado;
}