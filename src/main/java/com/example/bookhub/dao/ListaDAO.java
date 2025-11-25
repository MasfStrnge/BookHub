package com.example.bookhub.dao;

import com.example.bookhub.models.*;
import com.example.bookhub.utils.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListaDAO {

    public List<Lista> buscarListasPorPerfil(int id_perfil) {

        String sql = "SELECT id_lista, nome_lista, qt_livro, data_criacao " +
                     "FROM lista WHERE id_perfil = ?";

        List<Lista> listas = new ArrayList<>();

        try (Connection conexaoDB = ConexaoDB.getConnection();
             PreparedStatement stmt = conexaoDB.prepareStatement(sql)) {

            stmt.setInt(1, id_perfil);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Lista lista = new Lista();
                    lista.setId_lista(rs.getInt("id_lista"));
                    lista.setNome_lista(rs.getString("nome_lista"));
                    lista.setQt_livro(rs.getInt("qt_livro"));
                    lista.setData_criacao(rs.getDate("data_criacao").toLocalDate());

                    listas.add(lista);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listas;
    }
    public boolean adicionarLivroLista(Lista lista, Livro livro, StatusDoLivro status, Avaliacao avaliacao) {
        String sqlCheck = "SELECT COUNT(*) FROM lista_livro WHERE id_lista = ? AND id_livro = ?";
        String sqlInsert = "INSERT INTO lista_livro (id_lista, id_livro, data_adicionado, status, avaliacao) VALUES (?,?,?,?,?)";
        String sqlUpdateQt = "UPDATE lista SET qt_livro = qt_livro + 1 WHERE id_lista = ?";

        try (Connection conexaoDB = ConexaoDB.getConnection();
             PreparedStatement stmtCheck = conexaoDB.prepareStatement(sqlCheck);
             PreparedStatement stmtInsert = conexaoDB.prepareStatement(sqlInsert);
             PreparedStatement stmtUpdateQt = conexaoDB.prepareStatement(sqlUpdateQt)) {

            // Verifica duplicidade
            stmtCheck.setInt(1, lista.getId_lista());
            stmtCheck.setInt(2, livro.getId_livro());
            ResultSet rs = stmtCheck.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // já existe
            }

            // Atualiza objeto em memória
            livro.setStatus(status != null ? status : StatusDoLivro.INDEFINIDO);
            livro.setAvaliacao(avaliacao);

            // Prepara INSERT
            stmtInsert.setInt(1, lista.getId_lista());
            stmtInsert.setInt(2, livro.getId_livro());
            stmtInsert.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            stmtInsert.setString(4, livro.getStatus().name());
            stmtInsert.setInt(5, avaliacao != null ? avaliacao.getEstrelas() : 0);

            int resultado = stmtInsert.executeUpdate();

            if (resultado > 0) {
                stmtUpdateQt.setInt(1, lista.getId_lista());
                stmtUpdateQt.executeUpdate();

                lista.setQt_livro(lista.getQt_livro() + 1);
                return true;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean retirarLivroLista(Lista lista, Livro livro) {
        String sql = "UPDATE lista_livro SET status = 'INDEFINIDO' WHERE id_lista = ? AND id_livro = ?";

        try (Connection conexaoDB = ConexaoDB.getConnection();
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
    public boolean criarLista(Lista lista, Perfil perfil) {
        String sql = "INSERT INTO lista (id_perfil, nome_lista, qt_livro, data_criacao) VALUES (?,?,?,?)";

        try (Connection conexaoDB = ConexaoDB.getConnection();
             PreparedStatement stmt = conexaoDB.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, perfil.getId_perfil());
            stmt.setString(2, lista.getNome_lista());
            stmt.setInt(3, lista.getQt_livro());
            stmt.setDate(4, java.sql.Date.valueOf(LocalDate.now()));

            int resultado = stmt.executeUpdate();

            if (resultado > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        lista.setId_lista(rs.getInt(1));
                    }
                }
                return true;
            }

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deletarLista(Lista lista) {
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
    public boolean renomearLista(Lista lista,String novoNomeLista) {

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
    public Lista buscarListaFavoritos(int idPerfil) {
        String sql = "SELECT * FROM lista WHERE id_perfil = ? AND nome_lista = 'Favoritos'";

        try (Connection conexaoDB = ConexaoDB.getConnection();
             PreparedStatement stmt = conexaoDB.prepareStatement(sql)) {

            stmt.setInt(1, idPerfil);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Lista lista = new Lista();
                lista.setId_lista(rs.getInt("id_lista"));
                lista.setNome_lista(rs.getString("nome_lista"));
                return lista;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

