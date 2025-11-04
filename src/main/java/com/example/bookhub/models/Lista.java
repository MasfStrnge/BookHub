package com.example.bookhub.models;

import java.sql.Date;
import java.util.List;

public class Lista {
    private String nome_lista;
    private Integer qt_livro;
    private Date data_criacao;
    private List<Livro> livros;

    public Lista() {}

    public Lista(String nome_lista, Integer qt_livro, Date data_criacao) {
        this.nome_lista = nome_lista;
        this.qt_livro = qt_livro;
        this.data_criacao = data_criacao;
    }

    public String getNome_lista() {return nome_lista;}
    public void setNome_lista(String nome_lista) {this.nome_lista = nome_lista;}

    public Integer getQt_livro() {return qt_livro;}
    public void setQt_livro(Integer qt_livro) {this.qt_livro = qt_livro;}

    public Date getData_criacao() {return data_criacao;}
    public void setData_criacao(Date data_criacao) {this.data_criacao = data_criacao;}

    public List<Livro> getLivros() {return livros;}
    public void setLivros(List<Livro> livros) {this.livros = livros;}


}
