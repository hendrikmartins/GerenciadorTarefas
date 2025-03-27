package br.com.hendrikmartins.GerenciadorTarefas.model;

public class TarefaConcluida implements TarefaEstado {
    @Override
    public String getNome() {
        return "Concluída";
    }
}