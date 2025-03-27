package br.com.hendrikmartins.GerenciadorTarefas.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import br.com.hendrikmartins.GerenciadorTarefas.model.Tarefa;

public class NotificadorTarefa {

    public void notificarTarefasProximasDoPrazo(List<Tarefa> tarefas) {
        tarefas.forEach(tarefa -> {
            long diasAteDeadline = ChronoUnit.DAYS.between(LocalDate.now(), tarefa.getDeadline());
            if (diasAteDeadline <= 2) {
                CompletableFuture.runAsync(() -> {
                    System.out.println("Tarefa '" + tarefa.getTitulo() + "' próxima do prazo!");
                });
            }
        });
    }
}