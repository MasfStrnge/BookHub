package com.example.bookhub.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;

import javafx.stage.Stage;

public class PerfilController {
    @FXML private BorderPane rootPane;
    @FXML private StackPane painelPerfil;
    @FXML private Pane painelFotoPerfil, painelImagemFundo; //  Panes definido no FXML
    @FXML private ImageView imagemFundo,imagemPerfil, botaoPesquisar,botaoListas,botaoLogout;
    @FXML private HBox conteinerFavoritos;


    public void initialize() {
// Define imagens iniciais (padrão)
        carregarImagemFundo("img/imagemPerfilDefault.jpg");
        carregarFotoPerfil("img/imagemPerfilDefault.jpg");

        // Faz as imagens se ajustarem ao tamanho dos Panes
        imagemFundo.fitWidthProperty().bind(painelImagemFundo.widthProperty());
        imagemFundo.fitHeightProperty().bind(painelImagemFundo.heightProperty());

        imagemPerfil.fitWidthProperty().bind(painelFotoPerfil.widthProperty());
        imagemPerfil.fitHeightProperty().bind(painelFotoPerfil.heightProperty());
    }

    public void botaoPesquisar(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookhub/views/pesquisar-view.fxml"));
            BorderPane telaPesquisar = loader.load();
            Stage stage = (Stage) rootPane.getScene().getWindow();
            PesquisarController pesquisarController = loader.getController();
            stage.getScene().setRoot(telaPesquisar);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void botaoListas(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookhub/views/lista-view.fxml"));
            BorderPane telaListas = loader.load();
            Stage stage = (Stage) rootPane.getScene().getWindow();
            ListaController listaController = loader.getController();
            stage.getScene().setRoot(telaListas);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void botaoLogout(MouseEvent mouseEvent) {
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


    private void carregarImagemFundo(String caminho) {
        imagemFundo.setImage(new Image("file:" + caminho, true));
    }
    private void carregarFotoPerfil(String caminho) {
        imagemPerfil.setImage(new Image("file:" + caminho, true));
    }

    // ======= Métodos para o usuário editar =======
    @FXML private void editarImagemFundo() {
        File arquivo = escolherImagem();
        if (arquivo != null) {
            imagemFundo.setImage(new Image(arquivo.toURI().toString()));
        }
    }

    @FXML private void editarFotoPerfil() {
        File arquivo = escolherImagem();
        if (arquivo != null) {
            imagemPerfil.setImage(new Image(arquivo.toURI().toString()));
        }
    }

    private File escolherImagem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolher imagem");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );
        return fileChooser.showOpenDialog(new Stage());
    }

    public void setUsuarioLogado(String usuario) {
    }
}
