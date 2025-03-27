package br.com.hendrikmartins.GerenciadorTarefas.repository;

import br.com.hendrikmartins.GerenciadorTarefas.model.Tarefa;
import java.util.List;
import java.util.Optional;

public interface TarefaRepository {
    void salvar(Tarefa tarefa);
    Optional<Tarefa> carregar(String titulo);
    List<Tarefa> carregarTodas();
    void atualizar(Tarefa tarefa);
    void deletar(String titulo);
}