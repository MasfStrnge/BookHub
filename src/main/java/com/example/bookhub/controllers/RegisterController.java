package com.example.bookhub.controllers;

import com.example.bookhub.utils.ConexaoDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {

    public StackPane rootPane;
    @FXML private TextField CampoDeNome;
    @FXML private TextField CampoDeSobrenome;
    @FXML private TextField CampoDeUsuario;
    @FXML private TextField CampoDeEmail;
    @FXML private TextField CampoDeSenha;
    @FXML private ImageView BotaoBack;
    @FXML private Button BotaoCriar;

    public void CriarConta() throws SQLException {
        String nome = this.CampoDeNome.getText().trim();
        String sobrenome = this.CampoDeSobrenome.getText().trim();
        String usuario = this.CampoDeUsuario.getText().trim();
        String email = this.CampoDeEmail.getText().trim();
        String senha = this.CampoDeSenha.getText().trim();

        try (Connection conexaoDB = ConexaoDB.getConnection()) {

            PreparedStatement sql = conexaoDB.prepareStatement(
                    "INSERT INTO usuarios (nome_usuario, senha, email, nome, sobrenome) VALUES (?, ?, ?, ?, ?)");

            sql.setString(1,usuario);
            sql.setString(2,senha);
            sql.setString(3,email);
            sql.setString(4,nome);
            sql.setString(5,sobrenome);

            int resultado = sql.executeUpdate();

            if(resultado > 0) {
                System.out.println("CONTA CRIADA COM SUCESSO!");
            }
            else {
                System.out.println("FALHA NA CRIAÇÃO DA CONTA");
            }
        }
    }

    public void Voltar(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookhub/views/login-view.fxml"));
            Pane telaLogin = loader.load();

            rootPane.getChildren().setAll(telaLogin);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
