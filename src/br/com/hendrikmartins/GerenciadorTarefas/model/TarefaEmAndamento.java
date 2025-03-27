package br.com.hendrikmartins.GerenciadorTarefas.model;

public class TarefaEmAndamento implements TarefaEstado {
    @Override
    public String getNome() {
        return "Em Andamento";
    }
}