package com.example.bookhub.controllers;

import com.example.bookhub.models.Sessao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import javafx.stage.Stage;

public class ListaIndividualController {
    @FXML private BorderPane rootPane;
    @FXML private StackPane painelListaIndividual;
    @FXML private Pane listaFavoritos, listaLendo,ListaQueroLer,listaLidos; //  Panes definido no FXML
    @FXML private ImageView  botaoPesquisar,botaoPerfil,botaoLogout,botaoListas;
    @FXML private VBox conteinerLivrosDaLista;
    @FXML private Button botaoAdicionarLivro;

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

    public void adicionarLivro() throws SQLException {}
    public void consultarInfoLivro() throws SQLException {}

}
