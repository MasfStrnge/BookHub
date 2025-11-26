package com.example.bookhub.models;

public class Sessao {
    private static Usuario usuario;

    public static void setUsuario(Usuario u) {
        usuario = u;
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void limpar() {
        usuario = null;
    }

    @Override
    public String toString() {
        return "Sessao{" +
                "usuario=" + (usuario != null ? usuario.toString() : "null") +
                '}';
    }

}
