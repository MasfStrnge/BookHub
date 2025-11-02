package com.example.bookhub.models;

import java.util.Date;

public class Livro {
    private String titulo;
    private String autor;
    private Date publicacao;
    private int quantPagina;

    public Livro(String titulo, String autor, Date publicacao, int quantPagina) {
        this.titulo = titulo;
        this.autor = autor;
        this.publicacao = publicacao;
        this.quantPagina = quantPagina;
    }

    public String getTitulo(){ return titulo; }
    void setTitulo(String titulo) {this.titulo = titulo;}

    public String getAutor() {return autor;}
    void setAutor(String autor) {this.autor = autor;}

    public Date getPublicacao() {return publicacao;}
    public void setPublicacao(Date publicacao) {this.publicacao = publicacao;}

    public int getQuantPagina() {return quantPagina;}
    public void setQuantPagina(int quantPagina) {this.quantPagina = quantPagina;}
}
