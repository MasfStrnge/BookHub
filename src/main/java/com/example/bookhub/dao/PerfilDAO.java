package com.example.bookhub.dao;

import com.example.bookhub.models.Lista;
import com.example.bookhub.models.ListaPersonalizada;
import com.example.bookhub.models.Perfil;
import com.example.bookhub.models.Usuario;
import com.example.bookhub.utils.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.time.LocalDate;

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
    public boolean criarPerfil(Perfil perfil, Usuario usuario) {
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
                ResultSet chave = stmtPerfil.getGeneratedKeys();
                if (chave.next()) {
                    int idPerfil = chave.getInt(1);
                    perfil.setId_perfil(idPerfil);

                    Lista[] listasPadrao = {
                            perfil.getListaFavoritos(),
                            perfil.getListaQueroLer(),
                            perfil.getListaLendo(),
                            perfil.getListaLidos()
                    };

                    for (Lista lista : listasPadrao) {
                        stmtListas.setInt(1, idPerfil);
                        stmtListas.setString(2, lista.getNome_lista());
                        stmtListas.setInt(3, lista.getQt_livro());
                        stmtListas.setDate(4, java.sql.Date.valueOf(lista.getData_criacao()));
                        stmtListas.executeUpdate();
                    }

                    for (Lista listaPersonalizada : perfil.getListasPersonalizadas()) {
                        stmtListas.setInt(1, idPerfil);
                        stmtListas.setString(2, listaPersonalizada.getNome_lista());
                        stmtListas.setInt(3, listaPersonalizada.getQt_livro());
                        stmtListas.setDate(4, java.sql.Date.valueOf(listaPersonalizada.getData_criacao()));
                        stmtListas.executeUpdate();
                    }

                    return true;
                }
            }

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

                stmtListas.setInt(1, perfil.getId_perfil());
                ResultSet rsListas = stmtListas.executeQuery();

                while (rsListas.next()) {
                    String nomeLista = rsListas.getString("nome_lista");
                    int idLista = rsListas.getInt("id_lista");
                    int qtLivros = rsListas.getInt("qt_livro");
                    LocalDate dataCriacao = rsListas.getDate("data_criacao").toLocalDate();

                    switch (nomeLista) {
                        case "Favoritos":
                            perfil.getListaFavoritos().setId_lista(idLista);
                            perfil.getListaFavoritos().setId_perfil(perfil.getId_perfil());
                            perfil.getListaFavoritos().setQt_livro(qtLivros);
                            perfil.getListaFavoritos().setData_criacao(dataCriacao);
                            break;
                        case "Quero Ler":
                            perfil.getListaQueroLer().setId_lista(idLista);
                            perfil.getListaQueroLer().setId_perfil(perfil.getId_perfil());
                            perfil.getListaQueroLer().setQt_livro(qtLivros);
                            perfil.getListaQueroLer().setData_criacao(dataCriacao);
                            break;
                        case "Lendo":
                            perfil.getListaLendo().setId_lista(idLista);
                            perfil.getListaLendo().setId_perfil(perfil.getId_perfil());
                            perfil.getListaLendo().setQt_livro(qtLivros);
                            perfil.getListaLendo().setData_criacao(dataCriacao);
                            break;
                        case "Lidos":
                            perfil.getListaLidos().setId_lista(idLista);
                            perfil.getListaLidos().setId_perfil(perfil.getId_perfil());
                            perfil.getListaLidos().setQt_livro(qtLivros);
                            perfil.getListaLidos().setData_criacao(dataCriacao);
                            break;
                        default:
                            ListaPersonalizada listaPers = new ListaPersonalizada(perfil.getId_perfil(), nomeLista, qtLivros, dataCriacao);
                            listaPers.setId_lista(idLista);
                            perfil.getListasPersonalizadas().add(listaPers);
                            break;
                    }
                }

                return perfil;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}