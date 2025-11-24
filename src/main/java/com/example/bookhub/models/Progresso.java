package com.example.bookhub.models;

import java.time.LocalDate;

public class Progresso {
    private LocalDate dataProgresso;
    private String comentario;
    private int paginasLidas;

    public Progresso() {
        this.paginasLidas = 0;
    }

    public Progresso(LocalDate dataProgresso, String comentario,int progressoAtual) {
        this.dataProgresso = dataProgresso;
        this.comentario = comentario;
        this.paginasLidas = progressoAtual;
    }

    public LocalDate getDataProgresso() {return dataProgresso;}
    public void setDataProgresso(LocalDate dataProgresso) {this.dataProgresso = dataProgresso;}

    public String getComentario() {return comentario;}
    public void setComentario(String comentario) {this.comentario = comentario;}

    public int getPaginasLidas() {return paginasLidas;}
    public void setPaginasLidas(int paginasLidas) {this.paginasLidas = paginasLidas;}
}
