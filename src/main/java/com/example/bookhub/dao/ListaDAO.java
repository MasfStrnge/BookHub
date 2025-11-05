package com.example.bookhub.dao;

import com.example.bookhub.models.Lista;
import com.example.bookhub.models.Livro;
import com.example.bookhub.models.Perfil;
import com.example.bookhub.utils.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;

public class ListaDAO {
    public boolean adicionarLivroLista(Lista lista, Livro livro) {

        String sql = "INSERT INTO lista_livro (id_lista,id_livro) VALUES (?,?)";

        try(Connection conexaoDB = ConexaoDB.getConnection();
            PreparedStatement stmt = conexaoDB.prepareStatement(sql)) {

            stmt.setInt(1, lista.getId_lista());
            stmt.setInt(2, livro.getId_livro());

            int resultado = stmt.executeUpdate();

            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean retirarLivroLista(Lista lista, Livro livro) {

        String sql = "DELETE FROM lista_livro WHERE id_lista = ? AND id_livro = ?";

        try (Connection conexaoDB = ConexaoDB.getConnection();
             PreparedStatement stmt = conexaoDB.prepareStatement(sql)) {

            stmt.setInt(1,lista.getId_lista());
            stmt.setInt(2,livro.getId_livro());

            int resultado = stmt.executeUpdate();

            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean criarLista(Lista lista, Perfil perfil) {
        String sql = "INSERT INTO lista (id_perfil, nome_lista, qt_livro, data_criacao) VALUES (?,?,?,?)";

        try (Connection conexaoDB = ConexaoDB.getConnection();
             PreparedStatement stmt = conexaoDB.prepareStatement(sql)) {

            stmt.setInt(1,perfil.getId_perfil());
            stmt.setString(2,lista.getNome_lista());
            stmt.setInt(3,lista.getQt_livro());
            stmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));

            int resultado = stmt.executeUpdate();

            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean excluirLista(Lista lista) {
        String sql = "DELETE FROM lista WHERE id_lista = ?";

        try (Connection conexaoDB = ConexaoDB.getConnection();
             PreparedStatement stmt = conexaoDB.prepareStatement(sql)) {

            stmt.setInt(1,lista.getId_lista());

            int resultado = stmt.executeUpdate();

            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean alterarNomeLista(Lista lista,String novoNomeLista) {

        String sql = "UPDATE lista SET nome_lista = ? WHERE id_lista = ?";

        try (Connection conexaoDB = ConexaoDB.getConnection();
                 PreparedStatement stmt = conexaoDB.prepareStatement(sql)) {

                stmt.setString(1,novoNomeLista);
                stmt.setInt(2,lista.getId_lista());

                int resultado = stmt.executeUpdate();

                return resultado > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

        }

}

