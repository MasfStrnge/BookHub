package com.example.bookhub.controllers;

import com.example.bookhub.dao.UsuarioDAO;
import com.example.bookhub.models.Sessao;
import com.example.bookhub.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private Pane loginPane;
    @FXML private StackPane rootPane;
    @FXML private TextField CampoNomeDeUsuario;
    @FXML private PasswordField CampoDeSenha;
    @FXML private Label Cadastrar_se, LabelErro;
    @FXML private Button BotaoEntrar;

    @FXML
    public void entrar() {
        System.out.println("Método entrar() foi chamado!");

        String nomeUsuario = this.CampoNomeDeUsuario.getText().trim(); // Guarda string digitada
        String senha = this.CampoDeSenha.getText().trim();

        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(nomeUsuario);
        usuario.setSenha(senha);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean loginValido = usuarioDAO.login(usuario);

        if (loginValido) {
            try {
                Sessao.setUsuario(usuario);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookhub/views/perfil-view.fxml"));
                BorderPane telaPerfil = loader.load();

                Stage stage = (Stage) rootPane.getScene().getWindow();
                PerfilController perfilController = loader.getController();
                stage.getScene().setRoot(telaPerfil);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            LabelErro.setText("Usuário ou senha inválidos!");
        }
    }

    @FXML
    protected void registrarConta(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookhub/views/cadastro-view.fxml"));
            Pane telaRegistro = loader.load();

            rootPane.getChildren().setAll(telaRegistro);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




