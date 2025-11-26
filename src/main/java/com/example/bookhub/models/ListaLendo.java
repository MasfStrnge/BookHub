package com.example.bookhub.models;

import java.time.LocalDate;
import java.util.*;

public class ListaLendo extends Lista {

    public ListaLendo(int id_perfil, String nome_lista, int qt_livro, LocalDate data_criacao) {
        super(id_perfil, nome_lista, qt_livro, data_criacao);
    }
}
