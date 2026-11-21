
package com.comentacomigo.comenta_comigo_backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.comentacomigo.comenta_comigo_backend.model.Obra;
import com.comentacomigo.comenta_comigo_backend.repository.ObraRepository;

@Service
public class ObraService {

    private final ObraRepository repository;

    public ObraService(ObraRepository repository) {
        this.repository = repository;
    }

    public List<Obra> listar() {
        return repository.findAll();
    }

    public Obra buscarPorId(Long id) {
        return buscarEntidade(id);
    }

    public Obra criar(Obra obra) {
        return repository.save(obra);
    }

    public Obra atualizar(Long id, Obra dados) {
        Obra obra = buscarEntidade(id);

        obra.setTitulo(dados.getTitulo());
        obra.setTipo(dados.getTipo());
        obra.setAnoLancamento(dados.getAnoLancamento());
        obra.setSinopse(dados.getSinopse());
        obra.setCapaUrl(dados.getCapaUrl());

        return repository.save(obra);
    }

    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private Obra buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Obra não encontrada"));
    }
}
