package br.com.hendrikmartins.GerenciadorTarefas.service;

import java.time.LocalDate;
import java.util.List;

import br.com.hendrikmartins.GerenciadorTarefas.model.Tarefa;
import br.com.hendrikmartins.GerenciadorTarefas.model.TarefaEstado;

public interface IGerenciadorTarefas {
    void cadastrarTarefa(String titulo, String descricao, LocalDate deadline, TarefaEstado estado);
    List<Tarefa> listarTodasTarefas();
    List<Tarefa> filtrarTarefasPorEstado(TarefaEstado estado);
    List<Tarefa> listarTarefasOrdenadasPorDeadline();
    void notificarTarefasProximasDoPrazo();
    void atualizarStatusTarefa(String titulo, TarefaEstado novoEstado);
}