package com.example.bookhub.models;

public class Avaliacao {
    private Perfil perfil;
    private Livro livro;
    private int estrelas;

    public Avaliacao() {
        this.estrelas = 0;
    }

    public Avaliacao(int estrelas) {
        this.estrelas = estrelas; // atribui corretamente
    }

    public Avaliacao(Perfil perfil, Livro livro, int estrelas) {
        this.perfil = perfil;
        this.livro = livro;
        this.estrelas = estrelas;
    }

    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }

    public int getEstrelas() { return estrelas; }
    public void setEstrelas(int estrelas) { this.estrelas = estrelas; }
}
