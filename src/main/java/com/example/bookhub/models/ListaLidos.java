package com.example.bookhub.models;

import com.example.bookhub.DAO.ListaDAO;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaLidos extends Lista {
    private Map<Livro, Avaliacao> avaliacoes;

    public ListaLidos(int id_perfil, String nome_lista, int qt_livro, LocalDate data_criacao) {
        super(id_perfil, nome_lista, qt_livro, data_criacao);
        this.avaliacoes = new HashMap<>();
    }

    public void adicionarLido(Livro livro, Avaliacao avaliacao) {
        if (avaliacao == null || avaliacao.getEstrelas() <= 0) {
            throw new IllegalArgumentException("Livro deve ser avaliado antes de ser marcado como lido.");
        }
        livro.setStatus(StatusDoLivro.LIDO);
        getLivros().add(livro);
        setQt_livro(getLivros().size());

        avaliacoes.put(livro, avaliacao);

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
                        LocalDate.now()
                ));
    }

    public void adicionarLivro(Livro livro) {
        getLivros().add(livro);

        if (livro.getAvaliacao() != null) {
            avaliacoes.put(livro, livro.getAvaliacao());
        }

        setQt_livro(getLivros().size());
    }

    public Avaliacao getAvaliacao(Livro livro) {
        return avaliacoes.get(livro);
    }

    public Map<Livro, Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

}
