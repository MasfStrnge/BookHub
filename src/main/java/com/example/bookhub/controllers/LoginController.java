package com.example.bookhub.controllers;

import com.example.bookhub.DAO.PerfilDAO;
import com.example.bookhub.DAO.UsuarioDAO;
import com.example.bookhub.models.Perfil;
import com.example.bookhub.models.Sessao;
import com.example.bookhub.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private StackPane rootPane;
    @FXML private TextField CampoNomeDeUsuario;
    @FXML private PasswordField CampoDeSenha;

    @FXML
    public void entrar() {
        System.out.println("Método entrar() foi chamado!");

        String nomeUsuario = this.CampoNomeDeUsuario.getText().trim();
        String senha = this.CampoDeSenha.getText().trim();

        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(nomeUsuario);
        usuario.setSenha(senha);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioLogin = usuarioDAO.login(nomeUsuario,senha);

        if (usuarioLogin != null) {
            try {
                PerfilDAO perfilDAO = new PerfilDAO();
                Perfil perfil = perfilDAO.buscarPerfilPorUsuario(usuarioLogin);
                usuarioLogin.setPerfil(perfil);

                Sessao.setUsuario(usuarioLogin);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookhub/views/perfil-view.fxml"));
                BorderPane telaPerfil = loader.load();

                Stage stage = (Stage) rootPane.getScene().getWindow();
                PerfilController perfilController = loader.getController();
                stage.getScene().setRoot(telaPerfil);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            CampoNomeDeUsuario.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            CampoDeSenha.setStyle("-fx-border-color: red; -fx-border-width: 2;");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Atenção");
            alert.setHeaderText("FALHA NO LOGIN");
            alert.setContentText("Senha ou Usuario invalidos");
            alert.showAndWait();
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




