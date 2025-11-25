package com.example.bookhub.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Lista {
    private int id_lista;
    private int id_perfil;
    private String nome_lista;
    private int qt_livro;
    private LocalDate data_criacao;
    private List<Livro> livros;

    public Lista() {
        this.qt_livro = 0;
        this.livros = new ArrayList<>();
    }

    public Lista(int id_perfil, String nome_lista, int qt_livro, LocalDate data_criacao) {
        this.id_perfil = id_perfil;
        this.nome_lista = nome_lista;
        this.qt_livro = qt_livro;
        this.data_criacao = data_criacao;
        this.livros = new ArrayList<>();
    }

    public int getId_lista() { return id_lista; }
    public void setId_lista(int id_lista) { this.id_lista = id_lista; }

    public int getId_perfil() { return id_perfil; }
    public void setId_perfil(int id_perfil) { this.id_perfil = id_perfil; }

    public String getNome_lista() { return nome_lista; }
    public void setNome_lista(String nome_lista) { this.nome_lista = nome_lista; }

    public int getQt_livro() { return qt_livro; }
    public void setQt_livro(int qt_livro) { this.qt_livro = qt_livro; }

    public LocalDate getData_criacao() { return data_criacao; }
    public void setData_criacao(LocalDate data_criacao) { this.data_criacao = data_criacao; }

    public List<Livro> getLivros() { return livros; }
    public void setLivros(List<Livro> livros) { this.livros = livros; }
}
