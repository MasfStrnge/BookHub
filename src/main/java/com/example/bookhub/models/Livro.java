package com.example.bookhub.models;

import java.time.LocalDate;


public class Livro {
    private int id_livro;
    private String capa;
    private String titulo;
    private String autor;
    private LocalDate ano_publicacao;
    private String isbn;
    private String genero;
    private int qt_pagina;
    private String idioma;
    private String descricao;
    private LocalDate dataAdicionado;
    private StatusDoLivro status;
    private Avaliacao avaliacao;

    public Livro() {}

    public Livro(String capa, String titulo, String autor, LocalDate ano_publicacao, String isbn,String genero, int qt_pagina, String idioma, String descricao, StatusDoLivro status,Avaliacao avaliacao) {
        this.capa = capa;
        this.titulo = titulo;
        this.autor = autor;
        this.ano_publicacao = ano_publicacao;
        this.isbn = isbn;
        this.genero = genero;
        this.qt_pagina = qt_pagina;
        this.idioma = idioma;
        this.descricao = descricao;
        this.status = StatusDoLivro.INDEFINIDO;
        this.avaliacao = avaliacao;
    }

    public int getId_livro() {return id_livro;}
    public void setId_livro(int id_livro) {this.id_livro = id_livro;}

    public String getCapa() {return capa;}
    public void setCapa(String capa) {this.capa = capa;}

    public String getTitulo(){ return titulo; }
    public void setTitulo(String titulo) {this.titulo = titulo;}

    public String getAutor() {return autor;}
    public void setAutor(String autor) {this.autor = autor;}

    public LocalDate getPublicacao() {return ano_publicacao;}
    public void setPublicacao(LocalDate publicacao) {this.ano_publicacao = publicacao;}

    public String getIsbn() {return isbn;}
    public void setIsbn(String isbn) {this.isbn = isbn;}

    public String getGenero() {return genero;}
    public void setGenero(String genero) {this.genero = genero;}

    public int getQt_pagina() {return qt_pagina;}
    public void setQt_pagina(int qt_pagina) {this.qt_pagina = qt_pagina;}

    public String getIdioma() {return idioma;}
    public void setIdioma(String idioma) {this.idioma = idioma;}

    public LocalDate getDataAdicionado() {return dataAdicionado;}
    public void setDataAdicionado(LocalDate dataAdicionado) {this.dataAdicionado = dataAdicionado;}

    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}

    public StatusDoLivro getStatus() {return status;}
    public void setStatus(StatusDoLivro status) {this.status = status;}

    public Avaliacao getAvaliacao() {return avaliacao;}
    public void setAvaliacao(Avaliacao avaliacao) {this.avaliacao = avaliacao;}
}
