package br.com.hendrikmartins.GerenciadorTarefas.App;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import br.com.hendrikmartins.GerenciadorTarefas.model.*;
import br.com.hendrikmartins.GerenciadorTarefas.repository.TarefaRepository;
import br.com.hendrikmartins.GerenciadorTarefas.repository.TarefaRepositoryImpl;
import br.com.hendrikmartins.GerenciadorTarefas.service.GerenciadorTarefa;
import br.com.hendrikmartins.GerenciadorTarefas.service.IGerenciadorTarefas;
import br.com.hendrikmartins.GerenciadorTarefas.service.NotificadorTarefa;


public class Main {
    public static void main(String[] args) {
        TarefaRepository tarefaRepository = new TarefaRepositoryImpl();
        NotificadorTarefa notificadorTarefa = new NotificadorTarefa();
        IGerenciadorTarefas gerenciadorTarefas = new GerenciadorTarefa(tarefaRepository, notificadorTarefa);

        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 6) {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarTarefa(scanner, gerenciadorTarefas);
                    break;
                case 2:
                    listarTarefas(gerenciadorTarefas);
                    break;
                case 3:
                    filtrarTarefasPorStatus(scanner, gerenciadorTarefas);
                    break;
                case 4:
                    listarTarefasOrdenadasPorDeadline(gerenciadorTarefas);
                    break;
                case 5:
                    notificarTarefasProximasDoPrazo(gerenciadorTarefas);
                    break;
                case 6:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\nGerenciador de Tarefas");
        System.out.println("1. Cadastrar Tarefa");
        System.out.println("2. Listar Tarefas");
        System.out.println("3. Filtrar Tarefas por Status");
        System.out.println("4. Listar Tarefas Ordenadas por Deadline");
        System.out.println("5. Notificar Tarefas Próximas do Prazo");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarTarefa(Scanner scanner, IGerenciadorTarefas gerenciadorTarefas) {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Deadline (AAAA-MM-DD): ");
        LocalDate deadline = LocalDate.parse(scanner.nextLine());
        System.out.println("Status (1. PENDENTE, 2. EM_ANDAMENTO, 3. CONCLUIDO): ");
        int statusOpcao = scanner.nextInt();
        scanner.nextLine();

        TarefaEstado estado;
        switch (statusOpcao) {
            case 1:
                estado = new TarefaPendente();
                break;
            case 2:
                estado = new TarefaEmAndamento();
                break;
            case 3:
                estado = new TarefaConcluida();
                break;
            default:
                System.out.println("Status inválido. Tarefa cadastrada como PENDENTE.");
                estado = new TarefaPendente();
        }

        gerenciadorTarefas.cadastrarTarefa(titulo, descricao, deadline, estado);
        System.out.println("Tarefa cadastrada com sucesso!");
    }

    private static void listarTarefas(IGerenciadorTarefas gerenciadorTarefas) {
        List<Tarefa> tarefas = gerenciadorTarefas.listarTodasTarefas();
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada.");
        } else {
            tarefas.forEach(System.out::println);
        }
    }

    private static void filtrarTarefasPorStatus(Scanner scanner, IGerenciadorTarefas gerenciadorTarefas) {
        System.out.println("Status (1. PENDENTE, 2. EM_ANDAMENTO, 3. CONCLUIDO): ");
        int statusOpcao = scanner.nextInt();
        scanner.nextLine();

        TarefaEstado estado;
        switch (statusOpcao) {
            case 1:
                estado = new TarefaPendente();
                break;
            case 2:
                estado = new TarefaEmAndamento();
                break;
            case 3:
                estado = new TarefaConcluida();
                break;
            default:
                System.out.println("Status inválido.");
                return;
        }

        List<Tarefa> tarefasFiltradas = gerenciadorTarefas.filtrarTarefasPorEstado(estado);
        if (tarefasFiltradas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada com este status.");
        } else {
            tarefasFiltradas.forEach(System.out::println);
        }
    }

    private static void listarTarefasOrdenadasPorDeadline(IGerenciadorTarefas gerenciadorTarefas) {
        List<Tarefa> tarefasOrdenadas = gerenciadorTarefas.listarTarefasOrdenadasPorDeadline();
        if (tarefasOrdenadas.isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada.");
        } else {
            tarefasOrdenadas.forEach(System.out::println);
        }
    }

    private static void notificarTarefasProximasDoPrazo(IGerenciadorTarefas gerenciadorTarefas) {
        gerenciadorTarefas.notificarTarefasProximasDoPrazo();
    }
}