package com.example.bookhub.models;

import java.time.LocalDate;
import java.util.*;

public class ListaLendo extends Lista {
    private Map<Livro, List<Progresso>> progressoPorLivro;
    private Map<Livro, Avaliacao> avaliacaoPorLivro;

    public ListaLendo(int id_perfil, String nome_lista, int qt_livro, LocalDate data_criacao) {
        super(id_perfil, nome_lista, qt_livro, data_criacao);
        this.progressoPorLivro = new HashMap<>();
        this.avaliacaoPorLivro = new HashMap<>();
    }
    public void adicionarAvaliacao(Livro livro, Avaliacao avaliacao) {
        avaliacaoPorLivro.put(livro, avaliacao);
    }

    public List<Progresso> getProgresso(Livro livro) {return progressoPorLivro.getOrDefault(livro, new ArrayList<>());}

    public Avaliacao getAvaliacao(Livro livro) {return avaliacaoPorLivro.get(livro);}
}
