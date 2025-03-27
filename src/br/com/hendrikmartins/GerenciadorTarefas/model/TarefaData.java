package br.com.hendrikmartins.GerenciadorTarefas.model;

import java.time.LocalDate;
import java.util.Objects;


public class TarefaData {
    private String titulo;
    private String descricao;
    private LocalDate deadline;

    public TarefaData(String titulo, String descricao, LocalDate deadline) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.deadline = deadline;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TarefaData that = (TarefaData) o;
        return Objects.equals(titulo, that.titulo) &&
                Objects.equals(descricao, that.descricao) &&
                Objects.equals(deadline, that.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, descricao, deadline);
    }

    @Override
    public String toString() {
        return "TarefaData{" +
                "titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", deadline=" + deadline +
                '}';
    }
}