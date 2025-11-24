package com.example.bookhub.models;

import java.time.LocalDate;
import java.util.List;

public class ListaLendo extends Lista {
    private List<Progresso> progresso;
    private Avaliacao avaliacao;

    public ListaLendo(int id_perfil, String nome_lista, Integer qt_livro, LocalDate data_criacao, List<Progresso> progresso, Avaliacao avaliacao) {
        super(id_perfil,nome_lista,qt_livro,data_criacao);
        this.progresso = progresso;
        this.avaliacao = avaliacao;
    }

    public List<Progresso> getProgresso() {return progresso;}
    public void setProgresso(List<Progresso> progresso) {this.progresso = progresso;}

    public Avaliacao getAvaliacao() {return avaliacao;}
    public void setAvaliacao(Avaliacao avaliacao) {this.avaliacao = avaliacao;}
}
