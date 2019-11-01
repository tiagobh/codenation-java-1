package br.com.codenation.domain;

import java.time.LocalDate;
import java.util.*;

public class Time {
    private Long id;
    private String nome;
    private LocalDate dataCriacao;
    private String corUniformePrincipal;
    private String corUniformeSegundario;

    public Time(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSegundario) {
        this.id = id;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.corUniformePrincipal = corUniformePrincipal;
        this.corUniformeSegundario = corUniformeSegundario;
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

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getCorUniformePrincipal() {
        return corUniformePrincipal;
    }

    public void setCorUniformePrincipal(String corUniformePrincipal) {
        this.corUniformePrincipal = corUniformePrincipal;
    }

    public String getCorUniformeSegundario() {
        return corUniformeSegundario;
    }

    public void setCorUniformeSegundario(String corUniformeSegundario) {
        this.corUniformeSegundario = corUniformeSegundario;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Time)) return false;
        Time time = (Time) o;
        return getId().equals(time.getId()) &&
                getNome().equals(time.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome());
    }

    @Override
    public String toString() {
        return "Time{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", corUniformePrincipal='" + corUniformePrincipal + '\'' +
                ", corUniformeSegundario='" + corUniformeSegundario + '\'' +
                '}';
    }
}
