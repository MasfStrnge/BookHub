package com.example.bookhub.DAO;

import com.example.bookhub.models.Avaliacao;
import com.example.bookhub.models.Livro;
import com.example.bookhub.models.StatusDoLivro;
import com.example.bookhub.utils.ConexaoDB;

import java.sql.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public List<Livro> buscarLivrosPorLista(int idLista, String nomeLista) {
        String sql = "SELECT l.id_livro, l.capa, l.titulo, l.autor, l.ano_publicacao, " +
                "l.isbn, l.genero, l.qt_pagina, l.idioma, l.descricao, " +
                "ll.data_adicionado, ll.status, ll.avaliacao " +
                "FROM livro l " +
                "JOIN lista_livro ll ON l.id_livro = ll.id_livro " +
                "WHERE ll.id_lista = ?";

        List<Livro> livros = new ArrayList<>();

        try (Connection conexao = ConexaoDB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idLista);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Livro livro = new Livro();
                livro.setId_livro(rs.getInt("id_livro"));
                livro.setCapa(rs.getString("capa"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));

                int ano = rs.getInt("ano_publicacao");
                if (!rs.wasNull()) {
                    livro.setPublicacao(Year.of(ano));
                }

                livro.setIsbn(rs.getString("isbn"));
                livro.setGenero(rs.getString("genero"));
                livro.setQt_pagina(rs.getInt("qt_pagina"));
                livro.setIdioma(rs.getString("idioma"));
                livro.setDescricao(rs.getString("descricao"));

                Date data = rs.getDate("data_adicionado");
                if (data != null) {
                    livro.setDataAdicionado(data.toLocalDate());
                }

                String status = rs.getString("status");
                if (status != null) {
                    livro.setStatus(StatusDoLivro.valueOf(status.toUpperCase()));
                }


                if ("Favoritos".equalsIgnoreCase(nomeLista) || "Lidos".equalsIgnoreCase(nomeLista)) {
                    int estrelas = rs.getInt("avaliacao");
                    Avaliacao avaliacao = new Avaliacao(estrelas);
                    livro.setAvaliacao(avaliacao);
                }

                livros.add(livro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livros;
    }
    public List<Livro> exibirLivrosPesquisa(String termo) {
        String sql = "SELECT id_livro, capa, titulo, autor, ano_publicacao, isbn, genero, qt_pagina, idioma, descricao " +
                     "FROM livro " +
                     "WHERE titulo LIKE ? OR autor LIKE ? OR CAST(ano_publicacao AS CHAR) LIKE ? " +
                     "OR isbn LIKE ? OR genero LIKE ?";


        List<Livro> livros = new ArrayList<>();

        try (Connection conexao = ConexaoDB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            String filtro = "%" + termo + "%";
            stmt.setString(1, filtro);
            stmt.setString(2, filtro);
            stmt.setString(3, filtro);
            stmt.setString(4, filtro);
            stmt.setString(5, filtro);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Livro livro = new Livro();
                livro.setId_livro(rs.getInt("id_livro"));
                livro.setCapa(rs.getString("capa"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));

                int ano = rs.getInt("ano_publicacao");
                livro.setPublicacao(Year.of(ano));

                livro.setIsbn(rs.getString("isbn"));
                livro.setGenero(rs.getString("genero"));
                livro.setQt_pagina(rs.getInt("qt_pagina"));
                livro.setIdioma(rs.getString("idioma"));
                livro.setDescricao(rs.getString("descricao"));

                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livros;
    }
}
