package com.example.bookhub.controllers;

import com.example.bookhub.models.Sessao;
import com.example.bookhub.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.awt.*;
import java.io.IOException;

import javafx.stage.Stage;

public class InfoLivroController {

    @FXML private BorderPane rootPane;
    @FXML private StackPane painelInfoLivro;
    @FXML private Pane conteinerCapaLivro;
    @FXML private TextArea descricaoLivro;//  Panes definido no FXML
    @FXML private ImageView  botaoPesquisar,botaoPerfil,botaoLogout,botaoListas;

    @FXML private void initialize() {
        Usuario usuarioLogado = Sessao.getUsuario();
    }

    public void botaoPesquisar(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookhub/views/pesquisar-view.fxml"));
            BorderPane telaPesquisar = loader.load();

            Stage stage = (Stage) rootPane.getScene().getWindow();

            PesquisarController pesquisarControllerController = loader.getController();

            stage.getScene().setRoot(telaPesquisar);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void botaoPerfil(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookhub/views/perfil-view.fxml"));
            BorderPane telaPesquisar = loader.load();

            Stage stage = (Stage) rootPane.getScene().getWindow();

            PerfilController perfilController = loader.getController();

            stage.getScene().setRoot(telaPesquisar);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void botaoLogout(MouseEvent mouseEvent) {
        Sessao.limpar();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookhub/views/login-view.fxml"));
            Pane telaLogin = loader.load();
            Stage stage = (Stage) rootPane.getScene().getWindow();
            LoginController loginController = loader.getController();
            stage.getScene().setRoot(telaLogin);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void botaoListas(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookhub/views/listas-view.fxml"));
            BorderPane telaLista = loader.load();

            Stage stage = (Stage) rootPane.getScene().getWindow();

            ListaController listaController = loader.getController();

            stage.getScene().setRoot(telaLista);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
