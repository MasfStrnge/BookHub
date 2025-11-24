package com.example.bookhub.dao;

import com.example.bookhub.models.Lista;
import com.example.bookhub.models.Perfil;
import com.example.bookhub.models.Usuario;
import com.example.bookhub.utils.ConexaoDB;
import java.sql.*;

public class UsuarioDAO {

    public UsuarioDAO() {
    }

    public Usuario login(String nomeUsuario, String senha) {
        String sql = "SELECT * FROM usuario WHERE nome_usuario = ? AND senha = ?";
        try (Connection conexaoDB = ConexaoDB.getConnection();
             PreparedStatement stmt = conexaoDB.prepareStatement(sql)) {

            stmt.setString(1, nomeUsuario);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSobrenome(rs.getString("sobrenome"));
                usuario.setNomeUsuario(rs.getString("nome_usuario"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));

                return usuario;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean criarConta(Usuario usuario, Perfil perfil, Lista lista) {

        String sql = "INSERT INTO usuario (nome_usuario, senha, email, nome, sobrenome) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexaoDB = ConexaoDB.getConnection();
             PreparedStatement stmt = conexaoDB.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getNome());
            stmt.setString(5, usuario.getSobrenome());
            int resultado = stmt.executeUpdate();

            if (resultado > 0) {
                ResultSet chaves = stmt.getGeneratedKeys();
                if (chaves.next()) {
                    int idUsuario = chaves.getInt(1);
                    usuario.setId_usuario(idUsuario);

                    PerfilDAO perfilDAO = new PerfilDAO();
                    boolean perfilValido = perfilDAO.criarPerfil(perfil, usuario,lista);

                    if (perfilValido) {
                        System.out.println("Perfil criado com sucesso");
                        return true;
                    } else {
                        System.out.println("Falha na criacao do perfil");
                        return false;
                    }

                } else {
                    System.out.println("Erro");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}

