package br.com.hendrikmartins.GerenciadorTarefas.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.com.hendrikmartins.GerenciadorTarefas.model.*;
import br.com.hendrikmartins.GerenciadorTarefas.repository.TarefaRepository;


public class GerenciadorTarefa implements IGerenciadorTarefas {

    private final TarefaRepository tarefaRepository;
    private final NotificadorTarefa notificadorTarefa;

    public GerenciadorTarefa(TarefaRepository tarefaRepository, NotificadorTarefa notificadorTarefa) {
        this.tarefaRepository = tarefaRepository;
        this.notificadorTarefa = notificadorTarefa;
    }

    @Override
    public void cadastrarTarefa(String titulo, String descricao, LocalDate deadline, TarefaEstado estado) {
        TarefaData tarefaData = new TarefaData(titulo, descricao, deadline);
        Tarefa tarefa = new Tarefa.Builder().dados(tarefaData).estado(estado).build();
        tarefaRepository.salvar(tarefa);
    }

    @Override
    public List<Tarefa> listarTodasTarefas() {
        return tarefaRepository.carregarTodas();
    }

    @Override
    public List<Tarefa> filtrarTarefasPorEstado(TarefaEstado estado) {
        return tarefaRepository.carregarTodas().stream()
                .filter(tarefa -> tarefa.getEstadoNome().equals(estado.getNome()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Tarefa> listarTarefasOrdenadasPorDeadline() {
        return tarefaRepository.carregarTodas().stream()
                .sorted((t1, t2) -> t1.getDeadline().compareTo(t2.getDeadline()))
                .collect(Collectors.toList());
    }

    @Override
    public void notificarTarefasProximasDoPrazo() {
        notificadorTarefa.notificarTarefasProximasDoPrazo(tarefaRepository.carregarTodas());
    }

    @Override
    public void atualizarStatusTarefa(String titulo, TarefaEstado novoEstado) {
        tarefaRepository.carregar(titulo).ifPresent(tarefa -> {
            Tarefa tarefaAtualizada = tarefa.atualizarEstado(novoEstado);
            tarefaRepository.atualizar(tarefaAtualizada);
        });
    }
}