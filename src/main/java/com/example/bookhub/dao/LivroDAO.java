package com.example.bookhub.dao;


import com.example.bookhub.models.Lista;
import com.example.bookhub.models.Livro;
import com.example.bookhub.utils.ConexaoDB;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public List<Livro> buscarLivrosPorLista(int idLista) {
        String sql = "SELECT l.id_livro, l.capa, l.titulo, l.autor, l.ano_publicacao, " +
                     "l.isbn, l.genero, l.qt_pagina, l.idioma,l.descricao, ll.data_adicionado " +
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
                livro.setPublicacao(LocalDate.of(ano, 1, 1));
                livro.setIsbn(rs.getString("isbn"));
                livro.setGenero(rs.getString("genero"));
                livro.setQt_pagina(rs.getInt("qt_pagina"));
                livro.setIdioma(rs.getString("idioma"));
                livro.setDataAdicionado(rs.getDate("data_adicionado").toLocalDate());
                livro.setDescricao(rs.getString("descricao"));
                livros.add(livro);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return livros;
    }

    public Livro consultarInfoLivro(Livro livro) {
        String sql = "SELECT capa, titulo, autor, ano_publicacao, isbn, genero, qt_pagina, idioma, descricao " +
                "FROM livro WHERE id_livro = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, livro.getId_livro());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                livro.setCapa(rs.getString("capa"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setPublicacao(rs.getDate("ano_publicacao").toLocalDate());
                livro.setIsbn(rs.getString("isbn"));
                livro.setGenero(rs.getString("genero"));
                livro.setQt_pagina(rs.getInt("qt_pagina"));
                livro.setIdioma(rs.getString("idioma"));
                livro.setDescricao(rs.getString("descricao"));
                return livro;
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean adicionarLivroLista(int idLista, int idLivro) {
        String sqlCheck = "SELECT COUNT(*) FROM lista_livro WHERE id_lista = ? AND id_livro = ?";
        String sqlInsert = "INSERT INTO lista_livro (id_lista, id_livro, data_adicionado) VALUES (?, ?, CURRENT_DATE)";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck);
             PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {

            stmtCheck.setInt(1, idLista);
            stmtCheck.setInt(2, idLivro);
            ResultSet rs = stmtCheck.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Livro já adicionado");
                alerta.setHeaderText("Este livro já está na lista!");
                alerta.setContentText("Não é possível adicionar o mesmo livro duas vezes.");
                alerta.showAndWait();
                return false;
            }

            stmtInsert.setInt(1, idLista);
            stmtInsert.setInt(2, idLivro);
            return stmtInsert.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();

            Alert alertaErro = new Alert(Alert.AlertType.ERROR);
            alertaErro.setTitle("Erro");
            alertaErro.setHeaderText("Falha ao adicionar livro");
            alertaErro.setContentText("Ocorreu um erro ao tentar adicionar o livro na lista.");
            alertaErro.showAndWait();

            return false;
        }
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
                livro.setPublicacao(LocalDate.of(ano, 1, 1));

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
