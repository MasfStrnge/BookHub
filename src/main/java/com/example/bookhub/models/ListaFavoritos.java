package com.example.bookhub.models;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ListaFavoritos extends Lista {
    private Map<Livro, Avaliacao> avaliacaoPorLivro;

    public ListaFavoritos(int id_perfil, String nome_lista, int qt_livro, LocalDate data_criacao) {
        super(id_perfil, nome_lista, qt_livro, data_criacao);
        this.avaliacaoPorLivro = new HashMap<>();
    }

    public void adicionarFavorito(Livro livro, Avaliacao avaliacao, ListaLidos listaLidos) {
        if (avaliacao == null || avaliacao.getEstrelas() <= 0) {
            throw new IllegalArgumentException("Livro deve ser avaliado antes de ser favorito.");
        }

        livro.setStatus(StatusDoLivro.FAVORITO);
        getLivros().add(livro);
        avaliacaoPorLivro.put(livro, avaliacao);
        setQt_livro(getLivros().size());

        listaLidos.adicionarLido(livro, avaliacao);
    }

    public Avaliacao getAvaliacao(Livro livro) {return avaliacaoPorLivro.get(livro);}
    public Map<Livro, Avaliacao> getAvaliacoes() {return avaliacaoPorLivro;}
}
