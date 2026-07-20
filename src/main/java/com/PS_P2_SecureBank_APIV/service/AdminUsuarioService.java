package com.PS_P2_SecureBank_APIV.service;

import com.PS_P2_SecureBank_APIV.dto.request.ActualizarUsuarioAdminRequest;
import com.PS_P2_SecureBank_APIV.dto.request.ResetPasswordAdminRequest;
import com.PS_P2_SecureBank_APIV.dto.response.UsuarioAdminResponse;
import com.PS_P2_SecureBank_APIV.entity.Usuario;
import com.PS_P2_SecureBank_APIV.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditoriaService auditoriaService;

    public UsuarioAdminResponse buscarPorDni(String dni) {

        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return mapearUsuario(usuario);
    }

    @Transactional
    public UsuarioAdminResponse actualizarUsuario(
            Long id,
            ActualizarUsuarioAdminRequest request,
            String adminUsername) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setCorreo(request.getCorreo());
        usuario.setCelular(request.getCelular());
        usuario.setDireccion(request.getDireccion());

        Usuario actualizado = usuarioRepository.save(usuario);

        auditoriaService.registrarEvento(
                adminUsername,
                "USUARIO_ACTUALIZADO",
                "Actualización de datos del usuario DNI: " + usuario.getDni(),
                "127.0.0.1",
                "ADMIN_USUARIOS",
                true
        );

        return mapearUsuario(actualizado);
    }

    @Transactional
    public void resetPassword(
            Long id,
            ResetPasswordAdminRequest request,
            String adminUsername) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setPassword(passwordEncoder.encode(request.getNuevaPassword()));

        usuarioRepository.save(usuario);

        auditoriaService.registrarEvento(
                adminUsername,
                "PASSWORD_RESETEADO",
                "Reset de contraseña para usuario DNI: " + usuario.getDni(),
                "127.0.0.1",
                "ADMIN_USUARIOS",
                true
        );
    }

    private UsuarioAdminResponse mapearUsuario(Usuario usuario) {
        return UsuarioAdminResponse.builder()
                .id(usuario.getId())
                .dni(usuario.getDni())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .correo(usuario.getCorreo())
                .celular(usuario.getCelular())
                .direccion(usuario.getDireccion())
                .username(usuario.getUsername())
                .rol(usuario.getRol().getNombre().name())
                .activo(usuario.getActivo())
                .build();
    }
}