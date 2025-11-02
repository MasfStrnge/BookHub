package com.example.bookhub.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.stage.Stage;

public class ProfileController {

    @FXML private Pane painelFotoPerfil, painelImagemFundo; //  Panes definido no FXML
    @FXML private ImageView imagemFundo,imagemPerfil, botaoPesquisar,botaoPerfil,botaoListas,botaoLogout;
    @FXML private HBox conteinerFavoritos;


    public void initialize() {
// Define imagens iniciais (padrão)
        carregarImagemFundo("img/login_register_background.jpg");
        carregarFotoPerfil("img/imagemPerfilDefault.jpg");

        // Faz as imagens se ajustarem ao tamanho dos Panes
        imagemFundo.fitWidthProperty().bind(painelImagemFundo.widthProperty());
        imagemFundo.fitHeightProperty().bind(painelImagemFundo.heightProperty());

        imagemPerfil.fitWidthProperty().bind(painelFotoPerfil.widthProperty());
        imagemPerfil.fitHeightProperty().bind(painelFotoPerfil.heightProperty());
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
}