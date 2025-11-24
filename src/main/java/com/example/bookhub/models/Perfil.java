package com.example.bookhub.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Perfil {
    private int id_perfil;
    private int id_usuario;
    private String fotoPerfil;
    private String imagem_fundo;
    private List<Lista> listas;

    public Perfil() {
        this.fotoPerfil = "/com/example/bookhub/img/imagemPerfilDefault.jpg";
        this.imagem_fundo = "/com/example/bookhub/img/imagemFundoDefault.jpg";

        this.listas = new ArrayList<>();
        listas.add(new Lista(0,"Favoritos",0,LocalDate.now()));
        listas.add(new Lista(0,"Lendo",0,LocalDate.now()));
        listas.add(new Lista(0,"Quero Ler",0,LocalDate.now()));
        listas.add(new Lista(0,"Lidos",0, LocalDate.now()));
    }

    public Perfil(String fotoPerfil, String imagem_fundo) {
        this(); // chama construtor padrão.
        this.fotoPerfil = fotoPerfil;
        this.imagem_fundo = imagem_fundo;
    }

    public int getId_perfil() {return id_perfil;}
    public void setId_perfil(int id_perfil) {this.id_perfil = id_perfil;}

    public int getId_usuario() {return id_usuario;}
    public void setId_usuario(int id_usuario) {this.id_usuario = id_usuario;}

    public String getFotoPerfil() {return fotoPerfil;}
    public void setFotoPerfil(String fotoPerfil) {this.fotoPerfil = fotoPerfil;}

    public String getImagem_fundo() {return imagem_fundo;}
    public void setImagem_fundo(String imagem_fundo) {this.imagem_fundo = imagem_fundo;}

    public List<Lista> getListas() {return listas;}
    public void setListas(List<Lista> listas) {this.listas = listas;}
}
