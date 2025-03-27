package br.com.hendrikmartins.GerenciadorTarefas.model;

public class TarefaPendente implements TarefaEstado {
    @Override
    public String getNome() {
        return "Pendente";
    }
}