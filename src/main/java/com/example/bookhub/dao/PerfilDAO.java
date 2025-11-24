package com.example.bookhub.dao;

import com.example.bookhub.models.Lista;
import com.example.bookhub.models.Perfil;
import com.example.bookhub.models.Usuario;
import com.example.bookhub.utils.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;

public class PerfilDAO {

    public boolean alterarFotoPerfil(Perfil perfil, String novaFotoPerfil) {

        String sql = "UPDATE perfil SET fotoPerfil = ? WHERE id_perfil = ?";

        try (Connection conexaoDB = ConexaoDB.getConnection();
             PreparedStatement stmt = conexaoDB.prepareStatement(sql)) {

            stmt.setString(1, novaFotoPerfil);
            stmt.setInt(2, perfil.getId_perfil());
            stmt.executeUpdate();

            perfil.setFotoPerfil(novaFotoPerfil);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean alterarImagemFundo(Perfil perfil, String novaImagemFundo) {

        String sql = "UPDATE perfil SET imagem_fundo = ? WHERE id_perfil = ? ";

        try (Connection conexaoDB = ConexaoDB.getConnection();
             PreparedStatement stmt = conexaoDB.prepareStatement(sql)) {

            stmt.setString(1, novaImagemFundo);
            stmt.setInt(2, perfil.getId_perfil());
            stmt.executeUpdate();

            perfil.setImagem_fundo(novaImagemFundo);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public boolean criarPerfil(Perfil perfil, Usuario usuario, Lista lista) {
        String sqlPerfil = "INSERT INTO perfil(id_usuario,fotoPerfil,imagem_fundo) VALUES (?,?,?)";
        String sqlListas = "INSERT INTO lista(id_perfil, nome_lista, qt_livro, data_criacao) VALUES (?,?,?,?)";

        try (Connection conexaoDB = ConexaoDB.getConnection();
             PreparedStatement stmtPerfil = conexaoDB.prepareStatement(sqlPerfil, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtListas = conexaoDB.prepareStatement(sqlListas)) {

            stmtPerfil.setInt(1, usuario.getId_usuario());
            stmtPerfil.setString(2, perfil.getFotoPerfil());
            stmtPerfil.setString(3, perfil.getImagem_fundo());
            int resultadoPerfil = stmtPerfil.executeUpdate();

            if (resultadoPerfil > 0) {
                perfil.setId_usuario(usuario.getId_usuario());
                ResultSet chave = stmtPerfil.getGeneratedKeys();
                if(chave.next()) {
                    int idPerfil = chave.getInt(1);
                    lista.setId_perfil(idPerfil);

                    for (Lista listas : perfil.getListas()) {
                        stmtListas.setInt(1,idPerfil);
                        stmtListas.setString(2,listas.getNome_lista());
                        stmtListas.setInt(3,listas.getQt_livro());
                        stmtListas.setDate(4, java.sql.Date.valueOf(listas.getData_criacao()));
                        stmtListas.executeUpdate();
                    }
                    return true;
                }

            } else {
                System.out.println("ERRO NA CRIAÇAO DE PERFIL");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    public Perfil buscarPerfilPorUsuario(Usuario usuario) {
        String sqlPerfil = "SELECT * FROM perfil WHERE id_usuario = ?";
        String sqlListas = "SELECT * FROM lista WHERE id_perfil = ?";

        try (Connection conexaoDB = ConexaoDB.getConnection();
             PreparedStatement stmtPerfil = conexaoDB.prepareStatement(sqlPerfil);
             PreparedStatement stmtListas = conexaoDB.prepareStatement(sqlListas)) {

            stmtPerfil.setInt(1, usuario.getId_usuario());
            ResultSet rsPerfil = stmtPerfil.executeQuery();

            if (rsPerfil.next()) {
                Perfil perfil = new Perfil();
                perfil.setId_perfil(rsPerfil.getInt("id_perfil"));
                perfil.setId_usuario(rsPerfil.getInt("id_usuario"));
                perfil.setFotoPerfil(rsPerfil.getString("fotoPerfil"));
                perfil.setImagem_fundo(rsPerfil.getString("imagem_fundo"));

                // Carregar listas associadas ao perfil
                stmtListas.setInt(1, perfil.getId_perfil());
                ResultSet rsListas = stmtListas.executeQuery();

                while (rsListas.next()) {
                    Lista lista = new Lista();
                    lista.setId_lista(rsListas.getInt("id_lista"));
                    lista.setId_perfil(rsListas.getInt("id_perfil"));
                    lista.setNome_lista(rsListas.getString("nome_lista"));
                    lista.setQt_livro(rsListas.getInt("qt_livro"));
                    lista.setData_criacao(rsListas.getDate("data_criacao").toLocalDate());

                    perfil.getListas().add(lista);
                }

                return perfil;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}