
package com.comentacomigo.comenta_comigo_backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.comentacomigo.comenta_comigo_backend.model.Perfil;
import com.comentacomigo.comenta_comigo_backend.model.Usuario;
import com.comentacomigo.comenta_comigo_backend.repository.PerfilRepository;
import com.comentacomigo.comenta_comigo_backend.repository.UsuarioRepository;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;

    public PerfilService(PerfilRepository perfilRepository,
            UsuarioRepository usuarioRepository) {
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Perfil> listar() {
        return perfilRepository.findAll();
    }

    public Perfil buscarPorId(Long id) {
        return buscarEntidade(id);
    }

    public Perfil buscarPorUsuario(Long usuarioId) {
        return perfilRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Perfil não encontrado"));
    }

    public Perfil criar(Perfil dados) {

        if (dados.getUsuario() == null || dados.getUsuario().getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O usuário é obrigatório");
        }

        Long usuarioId = dados.getUsuario().getId();

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (perfilRepository.existsByUsuarioId(usuarioId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Este usuário já possui perfil");
        }

        Perfil perfil = new Perfil();
        perfil.setUsuario(usuario);
        perfil.setBio(dados.getBio());
        perfil.setFotoUrl(dados.getFotoUrl());

        return perfilRepository.save(perfil);
    }

    public Perfil atualizar(Long id, Perfil dados) {

        Perfil perfil = buscarEntidade(id);

        perfil.setBio(dados.getBio());
        perfil.setFotoUrl(dados.getFotoUrl());

        return perfilRepository.save(perfil);
    }

    public void excluir(Long id) {
        perfilRepository.delete(buscarEntidade(id));
    }

    private Perfil buscarEntidade(Long id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Perfil não encontrado"));
    }
}
