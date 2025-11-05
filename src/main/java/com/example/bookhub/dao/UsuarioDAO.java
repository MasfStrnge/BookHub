package com.example.bookhub.dao;

import com.example.bookhub.models.Usuario;
import com.example.bookhub.utils.ConexaoDB;
import java.sql.*;

public class UsuarioDAO {

    public UsuarioDAO() {}

    public boolean login(Usuario usuario) {
        String sql = "SELECT 1 FROM usuario WHERE nome_usuario = ? AND senha = ?";

        try (Connection conexaoDB = ConexaoDB.getConnection();
             PreparedStatement stmt = conexaoDB.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getSenha());

            ResultSet resultado = stmt.executeQuery();

            return resultado.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean criarConta(Usuario usuario) {

        String sql = "INSERT INTO usuario (nome_usuario, senha, email, nome, sobrenome) VALUES (?, ?, ?, ?, ?)";

        try(Connection conexaoDB = ConexaoDB.getConnection();
            PreparedStatement stmt = conexaoDB.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1,usuario.getNomeUsuario());
            stmt.setString(2,usuario.getSenha());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4,usuario.getNome());
            stmt.setString(5, usuario.getSobrenome());

            int resultado = stmt.executeUpdate();

            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
