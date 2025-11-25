package com.example.bookhub.models;

import java.time.LocalDate;

public class ListaQueroLer extends Lista {

    public ListaQueroLer(int id_perfil, String nome_lista, int qt_livro, LocalDate data_criacao) {
        super(id_perfil, nome_lista, qt_livro, data_criacao);
    }

    public void adicionarQueroLer(Livro livro) {
        livro.setStatus(StatusDoLivro.QUERO_LER);
        getLivros().add(livro);
        setQt_livro(getLivros().size());
    }
}
