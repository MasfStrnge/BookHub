package com.example.bookhub.models;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

public class Avaliacao {
    private Perfil perfil;
    private Livro livro;
    private int estrelas;

    public Avaliacao() {
        this.estrelas = 0;
    }

    public Avaliacao(int estrelas) {
        setEstrelas(estrelas);
    }

    public Avaliacao(Perfil perfil, Livro livro, int estrelas) {
        this.perfil = perfil;
        this.livro = livro;
        setEstrelas(estrelas);
    }

    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }

    public int getEstrelas() { return estrelas; }
    public void setEstrelas(int estrelas) {
        if (estrelas < 0 || estrelas > 5) {
            throw new IllegalArgumentException("Avaliação deve ser entre 0 e 5 estrelas.");
        }
        this.estrelas = estrelas;
    }

}
