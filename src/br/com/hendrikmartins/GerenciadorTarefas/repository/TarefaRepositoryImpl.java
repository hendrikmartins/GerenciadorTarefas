package br.com.hendrikmartins.GerenciadorTarefas.repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.hendrikmartins.GerenciadorTarefas.model.Tarefa;
import br.com.hendrikmartins.GerenciadorTarefas.model.TarefaData;
import br.com.hendrikmartins.GerenciadorTarefas.model.TarefaEstado;
import br.com.hendrikmartins.GerenciadorTarefas.model.TarefaPendente;
import br.com.hendrikmartins.GerenciadorTarefas.model.TarefaEmAndamento;
import br.com.hendrikmartins.GerenciadorTarefas.model.TarefaConcluida;

public class TarefaRepositoryImpl implements TarefaRepository {

    private final String diretorio = "./database/";

    @Override
    public void salvar(Tarefa tarefa) {
        File diretorioArquivo = new File(diretorio);
        if (!diretorioArquivo.exists()) {
            diretorioArquivo.mkdirs();
        }
        String nomeArquivo = diretorio + tarefa.getTitulo() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nomeArquivo), StandardCharsets.UTF_8))) {
            writer.write(tarefa.getTitulo() + "," + tarefa.getDescricao() + "," +
                    tarefa.getDeadline() + "," + tarefa.getEstadoNome());
        } catch (IOException e) {
            System.err.println("Erro ao salvar a tarefa: " + e.getMessage());
        }
    }

    @Override
    public Optional<Tarefa> carregar(String titulo) {
        String nomeArquivo = diretorio + titulo + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = reader.readLine();
            if (linha != null) {
                String[] dados = linha.split(",");
                TarefaData tarefaData = new TarefaData(dados[0], dados[1], LocalDate.parse(dados[2]));
                TarefaEstado estado = obterEstado(dados[3]);
                Tarefa tarefa = new Tarefa.Builder().dados(tarefaData).estado(estado).build();
                return Optional.of(tarefa);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Tarefa> carregarTodas() {
        List<Tarefa> tarefas = new ArrayList<>();
        File diretorioArquivos = new File(diretorio);
        File[] arquivos = diretorioArquivos.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isFile() && arquivo.getName().endsWith(".txt")) {
                    String titulo = arquivo.getName().replace(".txt", "");
                    carregar(titulo).ifPresent(tarefas::add);
                }
            }
        }
        return tarefas;
    }

    @Override
    public void atualizar(Tarefa tarefa) {
        salvar(tarefa);
    }

    @Override
    public void deletar(String titulo) {
        String nomeArquivo = diretorio + titulo + ".txt";
        File arquivo = new File(nomeArquivo);
        arquivo.delete();
    }

    private TarefaEstado obterEstado(String nomeEstado) {
        switch (nomeEstado) {
            case "Pendente":
                return new TarefaPendente();
            case "Em Andamento":
                return new TarefaEmAndamento();
            case "Concluída":
                return new TarefaConcluida();
            default:
                throw new IllegalArgumentException("Estado inválido: " + nomeEstado);
        }
    }
}