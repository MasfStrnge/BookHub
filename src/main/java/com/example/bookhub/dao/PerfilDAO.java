package com.example.bookhub.dao;

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

            stmt.setString(1,novaFotoPerfil);
            stmt.setInt(2,perfil.getId_perfil());
            stmt.executeUpdate();

            perfil.setFotoPerfil(novaFotoPerfil);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean alterarImagemFundo(Perfil perfil,String novaImagemFundo) {

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

}
