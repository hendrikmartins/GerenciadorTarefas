package br.com.hendrikmartins.GerenciadorTarefas.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class Tarefa {
    private final TarefaData dados;
    private final TarefaEstado estado;

    private Tarefa(Builder builder) {
        this.dados = builder.dados;
        this.estado = builder.estado;
        validar();
    }

    private void validar() {
        Optional.ofNullable(dados.getTitulo())
                .filter(t -> t.length() >= 5)
                .orElseThrow(() -> new IllegalArgumentException("Título deve ter pelo menos 5 caracteres."));

        Optional.ofNullable(dados.getDeadline())
                .filter(d -> !d.isBefore(LocalDate.now()))
                .orElseThrow(() -> new IllegalArgumentException("Deadline deve ser uma data futura."));

        Objects.requireNonNull(estado, "Estado não pode ser nulo.");
    }

    public Tarefa atualizarEstado(TarefaEstado novoEstado) {
        Objects.requireNonNull(novoEstado, "Novo estado não pode ser nulo.");
        return new Tarefa.Builder()
                .dados(this.dados)
                .estado(novoEstado)
                .build();
    }

    public String getTitulo() { return dados.getTitulo(); }
    public String getDescricao() { return dados.getDescricao(); }
    public LocalDate getDeadline() { return dados.getDeadline(); }
    public String getEstadoNome() { return estado.getNome(); }

    public boolean isPendente() { return estado instanceof TarefaPendente; }
    public boolean isEmAndamento() { return estado instanceof TarefaEmAndamento; }
    public boolean isConcluida() { return estado instanceof TarefaConcluida; }

    @Override
    public String toString() {
        return "Tarefa{" +
                "dados=" + dados +
                ", estado=" + estado.getNome() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarefa tarefa = (Tarefa) o;
        return Objects.equals(dados, tarefa.dados) &&
                Objects.equals(estado.getNome(), tarefa.estado.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(dados, estado.getNome());
    }

    public static class Builder {
        private TarefaData dados;
        private TarefaEstado estado;

        public Builder dados(TarefaData dados) {
            this.dados = dados;
            return this;
        }

        public Builder estado(TarefaEstado estado) {
            this.estado = estado;
            return this;
        }

        public Tarefa build() {
            return new Tarefa(this);
        }
    }
}