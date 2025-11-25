package com.example.bookhub.models;

import com.example.bookhub.dao.ListaDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaLidos extends Lista {
    private Map<Livro, Avaliacao> avaliacoes;
    private Map<Livro, List<Progresso>> historicoProgresso;

    public ListaLidos(int id_perfil, String nome_lista, int qt_livro, LocalDate data_criacao) {
        super(id_perfil, nome_lista, qt_livro, data_criacao);
        this.avaliacoes = new HashMap<>();
        this.historicoProgresso = new HashMap<>();
    }

    public void adicionarLido(Livro livro, Avaliacao avaliacao) {
        if (avaliacao == null || avaliacao.getEstrelas() <= 0) {
            throw new IllegalArgumentException("Livro deve ser avaliado antes de ser marcado como lido.");
        }
        livro.setStatus(StatusDoLivro.LIDO);
        getLivros().add(livro);
        setQt_livro(getLivros().size());

        avaliacoes.put(livro, avaliacao);

        historicoProgresso.put(livro, new ArrayList<>());
    }

    public static ListaLidos obterListaLidos(Usuario usuario) {
        ListaDAO listaDAO = new ListaDAO();

        List<Lista> listas = listaDAO.buscarListasPorPerfil(usuario.getPerfil().getId_perfil());

        return listas.stream()
                .filter(l -> l.getNome_lista().equals("Lidos"))
                .findFirst()
                .map(l -> new ListaLidos(usuario.getPerfil().getId_perfil(), l.getNome_lista(), l.getQt_livro(), l.getData_criacao()))
                .orElse(new ListaLidos(
                        usuario.getPerfil().getId_perfil(),
                        "Lidos",
                        0,
                        java.time.LocalDate.now()
                ));
    }

    public void adicionarProgresso(Livro livro, Progresso progresso) {
        historicoProgresso.putIfAbsent(livro, new ArrayList<>());
        historicoProgresso.get(livro).add(progresso);
    }

    public Avaliacao getAvaliacao(Livro livro) {return avaliacoes.get(livro);}
    public List<Progresso> getHistorico(Livro livro) {return historicoProgresso.getOrDefault(livro, new ArrayList<>());}
}
