package com.comentacomigo.comenta_comigo_backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.comentacomigo.comenta_comigo_backend.dto.ObraRequestDTO;
import com.comentacomigo.comenta_comigo_backend.dto.ObraResponseDTO;
import com.comentacomigo.comenta_comigo_backend.model.Obra;
import com.comentacomigo.comenta_comigo_backend.repository.ObraRepository;

@Service
public class ObraService {

    private final ObraRepository repository;

    public ObraService(ObraRepository repository) {
        this.repository = repository;
    }

    public List<ObraResponseDTO> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ObraResponseDTO buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    public ObraResponseDTO criar(ObraRequestDTO dto) {
        Obra obra = new Obra();
        preencher(obra, dto);
        return toResponse(repository.save(obra));
    }

    public ObraResponseDTO atualizar(Long id, ObraRequestDTO dto) {
        Obra obra = buscarEntidade(id);
        preencher(obra, dto);
        return toResponse(repository.save(obra));
    }

    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private Obra buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Obra não encontrada"));
    }

    private void preencher(Obra obra, ObraRequestDTO dto) {
        obra.setTitulo(dto.titulo());
        obra.setTipo(dto.tipo());
        obra.setAnoLancamento(dto.anoLancamento());
        obra.setSinopse(dto.sinopse());
        obra.setCapaUrl(dto.capaUrl());
    }

    private ObraResponseDTO toResponse(Obra obra) {
        return new ObraResponseDTO(
            obra.getId(),
            obra.getTitulo(),
            obra.getTipo(),
            obra.getAnoLancamento(),
            obra.getSinopse(),
            obra.getCapaUrl()
        );
    }
}