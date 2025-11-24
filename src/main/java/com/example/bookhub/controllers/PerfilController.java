package com.example.bookhub.controllers;

import com.example.bookhub.dao.ListaDAO;
import com.example.bookhub.dao.LivroDAO;
import com.example.bookhub.dao.PerfilDAO;
import com.example.bookhub.models.Lista;
import com.example.bookhub.models.Livro;
import com.example.bookhub.models.Sessao;
import com.example.bookhub.models.Usuario;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PerfilController {
    @FXML private BorderPane rootPane;
    @FXML private Pane painelFotoPerfil, painelImagemFundo;
    @FXML private ImageView imagemFundo, imagemPerfil;
    @FXML private HBox conteinerFavoritos;
    @FXML private Label labelNomeDeUsuario;
    private Lista listaFavoritos;
    private Usuario usuarioLogado;

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
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmação de Logout");
        alerta.setHeaderText("Tem certeza que deseja sair?");
        alerta.setContentText("Você será desconectada da sua sessão atual.");

        Optional<ButtonType> resultado = alerta.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
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

    @FXML private void initialize () {
            usuarioLogado = Sessao.getUsuario();

            ListaDAO listaDAO = new ListaDAO();
            listaFavoritos = listaDAO.buscarListaFavoritos(usuarioLogado.getPerfil().getId_perfil());

            atualizarPerfil();

        }

        private void atualizarPerfil () {
            labelNomeDeUsuario.setText(usuarioLogado.getNomeUsuario());
            Tooltip trocarFotoPerfil = new Tooltip("Clique para trocar sua foto de perfil!");
            trocarFotoPerfil.install(imagemPerfil, trocarFotoPerfil);

            Tooltip trocarImagemFundo = new Tooltip("Clique aqui para trocar sua Imagem de Fundo!");
            trocarImagemFundo.install(imagemFundo, trocarImagemFundo);


            String caminhoFoto = usuarioLogado.getPerfil().getFotoPerfil();
            String caminhoFundo = usuarioLogado.getPerfil().getImagem_fundo();

            if (caminhoFoto != null && !caminhoFoto.isEmpty()) {
                if (caminhoFoto.startsWith("/com/example/bookhub/")) {
                    imagemPerfil.setImage(new Image(getClass().getResource(usuarioLogado.getPerfil().getFotoPerfil()).toExternalForm()));
                } else {
                    imagemPerfil.setImage(new Image("file:" + caminhoFoto));
                }
            }

            if (caminhoFundo != null && !caminhoFundo.isEmpty()) {
                if (caminhoFundo.startsWith("/com/example/bookhub/")) {
                    imagemFundo.setImage(new Image(getClass().getResource(caminhoFundo).toExternalForm()));
                } else {
                    imagemFundo.setImage(new Image("file:" + caminhoFundo));
                }
            }

            imagemFundo.setPreserveRatio(false);
            imagemFundo.fitWidthProperty().bind(painelImagemFundo.widthProperty());
            imagemFundo.fitHeightProperty().bind(painelImagemFundo.heightProperty());

            Rectangle clip = new Rectangle();
            clip.widthProperty().bind(imagemFundo.fitWidthProperty());
            clip.heightProperty().bind(imagemFundo.fitHeightProperty());
            clip.setArcWidth(30);
            clip.setArcHeight(30);
            imagemFundo.setClip(clip);

            imagemPerfil.setPreserveRatio(false);
            imagemPerfil.fitWidthProperty().bind(painelFotoPerfil.widthProperty());
            imagemPerfil.fitHeightProperty().bind(painelFotoPerfil.heightProperty());

            Circle clipFoto = new Circle();
            clipFoto.radiusProperty().bind(Bindings.min(imagemPerfil.fitWidthProperty(), imagemPerfil.fitHeightProperty()).divide(2));
            clipFoto.centerXProperty().bind(imagemPerfil.fitWidthProperty().divide(2));
            clipFoto.centerYProperty().bind(imagemPerfil.fitHeightProperty().divide(2));
            imagemPerfil.setClip(clipFoto);

            carregarLivrosFavoritos(listaFavoritos);

        }
        public void carregarLivrosFavoritos (Lista lista){

            Usuario usuarioLogado = Sessao.getUsuario();

            conteinerFavoritos.getChildren().clear();

            LivroDAO livroDAO = new LivroDAO();
            List<Livro> livros = livroDAO.buscarLivrosPorLista(lista.getId_lista());

            if (livros.isEmpty()) {
                Label vazioLabel = new Label("Sua lista de livros favoritos está vazia, adicione para exibirmos.");
                vazioLabel.setFont(Font.font("Alexandria", 24));
                vazioLabel.setStyle("-fx-text-fill: rgba(0, 0, 0, 0.56);");
                vazioLabel.setAlignment(Pos.CENTER);
                HBox.setMargin(vazioLabel, new Insets(0, 0, 0, 500));

                conteinerFavoritos.setAlignment(Pos.CENTER);
                conteinerFavoritos.getChildren().add(vazioLabel);
                return;
            }

            for (Livro livro : livros) {

                Pane conteinerCapaLivro = new Pane();
                conteinerCapaLivro.setPrefHeight(344);
                conteinerCapaLivro.setPrefWidth(239);
                conteinerCapaLivro.getStyleClass().add("conteinerCapaLivro");

                ImageView capa = new ImageView();
                capa.setPreserveRatio(false);
                capa.fitWidthProperty().bind(conteinerCapaLivro.widthProperty());
                capa.fitHeightProperty().bind(conteinerCapaLivro.heightProperty());
                capa.setImage(new Image(getClass().getResource("/com/example/bookhub/" + livro.getCapa()).toExternalForm()));

                Rectangle clip = new Rectangle();
                clip.widthProperty().bind(capa.fitWidthProperty());
                clip.heightProperty().bind(capa.fitHeightProperty());
                clip.setArcWidth(30);
                clip.setArcHeight(30);
                capa.setClip(clip);

                DropShadow sombra = new DropShadow();
                sombra.setColor(Color.color(0, 0, 0, 0.3));
                conteinerCapaLivro.setEffect(sombra);
                sombra.setOffsetX(0);
                sombra.setOffsetY(4);
                sombra.setBlurType(BlurType.ONE_PASS_BOX);

                conteinerCapaLivro.getChildren().add(capa);
                conteinerFavoritos.getChildren().add(conteinerCapaLivro);
            }
        }

        @FXML private void trocarImagemFundo (MouseEvent mouseEvent){
            Usuario usuarioLogado = Sessao.getUsuario();
            FileChooser escolherImagem = new FileChooser();
            escolherImagem.setTitle("Selecione uma Imagem");

            escolherImagem.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
            );

            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            File arquivoSelecionado = escolherImagem.showOpenDialog(stage);

            if (arquivoSelecionado != null) {
                String caminhoImagem = arquivoSelecionado.getAbsolutePath();

                PerfilDAO perfilDAO = new PerfilDAO();
                perfilDAO.alterarImagemFundo(usuarioLogado.getPerfil(), caminhoImagem);

                atualizarPerfil();
            }
        }
        @FXML private void trocarImagemPerfil (MouseEvent mouseEvent){
            Usuario usuarioLogado = Sessao.getUsuario();
            FileChooser escolherImagem = new FileChooser();
            escolherImagem.setTitle("Selecione uma Imagem");

            escolherImagem.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
            );

            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            File arquivoSelecionado = escolherImagem.showOpenDialog(stage);

            if (arquivoSelecionado != null) {
                String caminhoImagem = arquivoSelecionado.getAbsolutePath();

                PerfilDAO perfilDAO = new PerfilDAO();
                perfilDAO.alterarFotoPerfil(usuarioLogado.getPerfil(), caminhoImagem);

                atualizarPerfil();

            }

        }

}