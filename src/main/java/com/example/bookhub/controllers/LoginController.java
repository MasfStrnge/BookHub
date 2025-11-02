package com.example.bookhub.controllers;


import com.example.bookhub.utils.ConexaoDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.*;

public class LoginController {

    @FXML private Pane loginPane;
    @FXML private StackPane rootPane;
    @FXML private TextField CampoNomeDeUsuario;
    @FXML private PasswordField CampoDeSenha;
    @FXML private Label Cadastrar_se;
    @FXML private Button BotaoEntrar;

    @FXML
    protected void entrar() throws SQLException {

        String usuario = this.CampoNomeDeUsuario.getText().trim(); // Guarda string digitada
        String senha = this.CampoDeSenha.getText().trim();

        try (Connection conexaoDB = ConexaoDB.getConnection()) {

            PreparedStatement stmt = conexaoDB.prepareStatement(
                    "SELECT 1 FROM usuarios WHERE nome_usuario = ? AND senha = ?");

            stmt.setString(1, usuario);
            stmt.setString(2, senha);

            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookhub/views/profile-view.fxml"));
                    Pane telaLogin = loader.load();

                    rootPane.getChildren().setAll(telaLogin);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void registrarConta(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookhub/views/register-view.fxml"));
            Pane telaRegistro = loader.load();

            rootPane.getChildren().setAll(telaRegistro);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




