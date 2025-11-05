package com.example.bookhub.controllers;

import com.example.bookhub.models.Sessao;
import com.example.bookhub.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import java.io.IOException;

import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class PerfilController {
    @FXML private BorderPane rootPane;
    @FXML private StackPane painelPerfil;
    @FXML private Pane painelFotoPerfil, painelImagemFundo; //  Panes definido no FXML
    @FXML private ImageView imagemFundo,imagemPerfil, botaoPesquisar,botaoListas,botaoLogout;
    @FXML private HBox conteinerFavoritos;
    @FXML private Label labelNomeDeUsuario;

    private Usuario usuarioLogado;

    @FXML private void initialize() {
        usuarioLogado = Sessao.getUsuario();
        atualizarPerfil();

        //imagemFundo.fitWidthProperty().bind(painelImagemFundo.widthProperty());
        //imagemFundo.fitHeightProperty().bind(painelImagemFundo.heightProperty());
       // imagemFundo.setPreserveRatio(false); // cobre 100% do painel
       // imagemFundo.setSmooth(true);

        //imagemPerfil.fitWidthProperty().bind(painelFotoPerfil.widthProperty());
       // imagemPerfil.fitHeightProperty().bind(painelFotoPerfil.heightProperty());
        //imagemPerfil.setPreserveRatio(true);
       // imagemPerfil.setSmooth(true);

    }
    private void atualizarPerfil() {
        labelNomeDeUsuario.setText(usuarioLogado.getNomeUsuario());
       // imagemPerfil.setImage(new Image(getClass().getResource(usuarioLogado.getPerfil().getFotoPerfil()).toExternalForm()));
       // imagemFundo.setImage(new Image(getClass().getResource(usuarioLogado.getPerfil().getFotoPerfil()).toExternalForm()));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookhub/views/listas-view.fxml"));
            BorderPane telaLista = loader.load();

            Stage stage = (Stage) rootPane.getScene().getWindow();

            ListaController listaController = loader.getController();

            stage.getScene().setRoot(telaLista);

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

}
