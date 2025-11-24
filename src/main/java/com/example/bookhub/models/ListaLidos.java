package com.example.bookhub.models;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaLidos extends Lista {
    private Map<Livro, Avaliacao> avaliacoes;
    private Map<Livro, List<Progresso>> historicoProgresso;

    public ListaLidos(int idPerfil, String nomeLista, LocalDate dataCriacao) {
        super(idPerfil, nomeLista, 0, dataCriacao);
        this.avaliacoes = new HashMap<>();
        this.historicoProgresso = new HashMap<>();
    }
}
