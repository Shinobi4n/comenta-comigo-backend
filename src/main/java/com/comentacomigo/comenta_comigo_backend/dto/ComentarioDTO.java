
package com.comentacomigo.comenta_comigo_backend.dto;

public class ComentarioDTO {

    private String texto;
    private Long usuarioId;
    private Long obraId;
    private Integer nota;

    public ComentarioDTO() {
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getObraId() {
        return obraId;
    }

    public void setObraId(Long obraId) {
        this.obraId = obraId;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }
}
