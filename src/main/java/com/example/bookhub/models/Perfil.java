package com.example.bookhub.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Perfil {
    private int id_perfil;
    private int id_usuario;
    private String fotoPerfil;
    private String imagem_fundo;

    private final ListaFavoritos listaFavoritos;
    private final ListaLendo listaLendo;
    private final ListaLidos listaLidos;
    private final ListaQueroLer listaQueroLer;
    private final List<ListaPersonalizada> listasPersonalizadas;

    public Perfil() {
        this.fotoPerfil = "/com/example/bookhub/img/imagemPerfilDefault.jpg";
        this.imagem_fundo = "/com/example/bookhub/img/imagemFundoDefault.jpg";

        this.listaFavoritos = new ListaFavoritos(0, "Favoritos",0, LocalDate.now());
        this.listaLendo = new ListaLendo(0,"Lendo",0,LocalDate.now());
        this.listaQueroLer = new ListaQueroLer(0, "Quero Ler",0, LocalDate.now());
        this.listaLidos = new ListaLidos(0, "Lidos",0, LocalDate.now());

        this.listasPersonalizadas = new ArrayList<>();
    }

    public Perfil(String fotoPerfil, String imagem_fundo) {
        this();
        this.fotoPerfil = fotoPerfil;
        this.imagem_fundo = imagem_fundo;
    }

    public int getId_perfil() { return id_perfil; }
    public void setId_perfil(int id_perfil) { this.id_perfil = id_perfil; }

    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }

    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }

    public String getImagem_fundo() { return imagem_fundo; }
    public void setImagem_fundo(String imagem_fundo) { this.imagem_fundo = imagem_fundo; }

    public ListaFavoritos getListaFavoritos() { return listaFavoritos; }
    public ListaLendo getListaLendo() { return listaLendo; }
    public ListaLidos getListaLidos() { return listaLidos; }
    public ListaQueroLer getListaQueroLer() { return listaQueroLer; }
    public List<ListaPersonalizada> getListasPersonalizadas() { return listasPersonalizadas; }

    @Override
    public String toString() {
        return "Perfil{" +
                "id_perfil=" + id_perfil +
                ", id_usuario=" + id_usuario +
                ", fotoPerfil='" + fotoPerfil + '\'' +
                ", imagem_fundo='" + imagem_fundo + '\'' +
                ", listaFavoritos=" + listaFavoritos +
                ", listaLendo=" + listaLendo +
                ", listaLidos=" + listaLidos +
                ", listaQueroLer=" + listaQueroLer +
                ", listasPersonalizadas=" + listasPersonalizadas +
                '}';
    }

}
