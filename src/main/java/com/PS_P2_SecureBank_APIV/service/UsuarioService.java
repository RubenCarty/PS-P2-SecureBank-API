package com.PS_P2_SecureBank_APIV.service;

import com.PS_P2_SecureBank_APIV.entity.Usuario;
import com.PS_P2_SecureBank_APIV.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario guardar(Usuario usuario){

        if(usuario.getActivo() == null){
            usuario.setActivo(true);
        }

        if(usuario.getFechaRegistro() == null){
            usuario.setFechaRegistro(LocalDateTime.now());
        }

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorUsername(String username){
        return usuarioRepository.findByUsername(username);
    }

    public Optional<Usuario> buscarPorCorreo(String correo){
        return usuarioRepository.findByCorreo(correo);
    }

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id){
        return usuarioRepository.findById(id);
    }

    public void eliminar(Long id){
        usuarioRepository.deleteById(id);
    }
}