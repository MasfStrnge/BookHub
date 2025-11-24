package com.example.bookhub.models;

import java.time.LocalDate;

public class ListaFavoritos extends Lista {
    private Avaliacao avaliacao;

    public ListaFavoritos(int id_perfil, String nome_lista, Integer qt_livro, LocalDate data_criacao, Avaliacao avaliacao) {
        super(id_perfil,nome_lista,qt_livro,data_criacao);
        this.avaliacao = avaliacao;
    }

    public Avaliacao getAvaliacao() {return avaliacao;}
    public void setAvaliacao(Avaliacao avaliacao) {this.avaliacao = avaliacao;}
}
