package com.example.bookhub.models;

import java.util.Date;

public class Livro {
    private int id_livro;
    private String capa;
    private String titulo;
    private String autor;
    private Date ano_publicacao;
    private String isbn;
    private String genero;
    private int qt_pagina;
    private String idioma;

    public Livro() {}

    public Livro(String capa, String titulo, String autor, Date ano_publicacao, String isbn,String genero, int qt_pagina, String idioma) {
        this.capa = capa;
        this.titulo = titulo;
        this.autor = autor;
        this.ano_publicacao = ano_publicacao;
        this.isbn = isbn;
        this.genero = genero;
        this.qt_pagina = qt_pagina;
        this.idioma = idioma;
    }

    public int getId_livro() {return id_livro;}
    public void setId_livro(int id_livro) {this.id_livro = id_livro;}

    public String getCapa() {return capa;}
    public void setCapa(String capa) {this.capa = capa;}

    public String getTitulo(){ return titulo; }
    void setTitulo(String titulo) {this.titulo = titulo;}

    public String getAutor() {return autor;}
    void setAutor(String autor) {this.autor = autor;}

    public Date getPublicacao() {return ano_publicacao;}
    public void setPublicacao(Date publicacao) {this.ano_publicacao = publicacao;}

    public String getIsbn() {return isbn;}
    public void setIsbn(String isbn) {this.isbn = isbn;}

    public String getGenero() {return genero;}
    public void setGenero(String genero) {this.genero = genero;}

    public int getQt_pagina() {return qt_pagina;}
    public void setQt_pagina(int qt_pagina) {this.qt_pagina = qt_pagina;}

    public String getIdioma() {return idioma;}
    public void setIdioma(String idioma) {this.idioma = idioma;}
}
