package br.votacao.mobile.api;

import java.io.Serializable;
import java.util.List;

public class Candidato implements Serializable {

    private Long id;

    private String nome;

    private String foto;

    private String detalhes;

    private String partido;

    private String propostas;

    private String site;

    private Integer totalVotos;

    public Candidato() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String autor) {
        this.foto = foto;
    }

    public Integer getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(Integer totalVotos) {
        this.totalVotos = totalVotos;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public String getPropostas() {
        return propostas;
    }

    public void setPropostas(String propostas) {
        this.propostas = propostas;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }


    @Override
    public String toString() {
        return "Candidato{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", foto='" + foto + '\'' +
                ", detalhes='" + detalhes + '\'' +
                ", partido=" + partido +
                ", propostas=" + propostas +
                ", site=" + site +
                ", totalVotos=" + totalVotos +
                '}';
    }
}