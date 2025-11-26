package com.example.bookhub.controllers;

import com.example.bookhub.DAO.ListaDAO;
import com.example.bookhub.DAO.LivroDAO;
import com.example.bookhub.models.Lista;
import com.example.bookhub.models.Livro;
import com.example.bookhub.models.Sessao;
import com.example.bookhub.models.Usuario;
import com.example.bookhub.utils.NavegacaoTelas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ListaIndividualController {
    @FXML private BorderPane rootPane;
    @FXML private VBox conteinerLivrosDaLista;
    @FXML private Label nomeListaPrincipal;
    @FXML private void botaoPesquisar(MouseEvent mouseEvent) {
        NavegacaoTelas.trocarTelaBorder(rootPane, "/com/example/bookhub/views/pesquisar-view.fxml");
    }
    @FXML private void botaoPerfil(MouseEvent mouseEvent) {
        NavegacaoTelas.trocarTelaBorder(rootPane, "/com/example/bookhub/views/perfil-view.fxml");
    }
    @FXML private void botaoLogout(MouseEvent mouseEvent) {
        NavegacaoTelas.logout(rootPane, "/com/example/bookhub/views/login-view.fxml");
    }
    @FXML private void botaoListas(MouseEvent mouseEvent) {
        NavegacaoTelas.trocarTelaBorder(rootPane, "/com/example/bookhub/views/listas-view.fxml");
    }
    private Lista lista;

    public void setLista(Lista lista) {
        this.lista = lista;
        nomeListaPrincipal.setText(lista.getNome_lista());
        carregarLivros(lista);
    }

    @FXML private void initialize() {
        Usuario usuarioLogado = Sessao.getUsuario();
    }

    public void carregarLivros(Lista lista) {

        Usuario usuarioLogado = Sessao.getUsuario();

        conteinerLivrosDaLista.getChildren().clear();

        LivroDAO livroDAO = new LivroDAO();
        List<Livro> livros = livroDAO.buscarLivrosPorLista(lista.getId_lista(),lista.getNome_lista());

        if (livros.isEmpty()) {
            Label vazioLabel = new Label("Sua lista está vazia, pesquise livros e os adicione para exibirmos aqui.");
            vazioLabel.setFont(Font.font("Alexandria", 24));
            vazioLabel.setStyle("-fx-text-fill: rgba(0, 0, 0, 0.56);");
            vazioLabel.setAlignment(Pos.CENTER);
            VBox.setMargin(vazioLabel, new Insets(250, 0, 0, 0));

            conteinerLivrosDaLista.setAlignment(Pos.CENTER);
            conteinerLivrosDaLista.getChildren().add(vazioLabel);
            return;
        }

        for (Livro livro : livros) {

            StackPane novoPainelLivro = new StackPane();
            novoPainelLivro.setPrefHeight(60);
            novoPainelLivro.setPrefWidth(1269);
            novoPainelLivro.getStyleClass().add("LivroDaLista");

            DropShadow sombra = new DropShadow();
            sombra.setColor(Color.color(0, 0, 0, 0.3));
            novoPainelLivro.setEffect(sombra);
            sombra.setOffsetX(0);
            sombra.setOffsetY(4);
            sombra.setBlurType(BlurType.ONE_PASS_BOX);

            Label titulo = new Label(livro.getTitulo());
            StackPane.setAlignment(titulo, Pos.CENTER_LEFT);
            StackPane.setMargin(titulo, new Insets(0, 0, 0, 20));
            titulo.setFont(Font.font("Alexandria", 20));
            titulo.getStyleClass().add("Labels");

            Label autor = new Label(livro.getAutor());
            StackPane.setAlignment(autor, Pos.CENTER);
            StackPane.setMargin(autor, new Insets(0, 500, 0, 0));
            autor.setFont(Font.font("Alexandria", 20));
            autor.getStyleClass().add("Labels");

            Label dataAdicionado = new Label(livro.getDataAdicionado().toString());
            StackPane.setAlignment(dataAdicionado, Pos.CENTER_RIGHT);
            StackPane.setMargin(dataAdicionado, new Insets(0, 800, 0, 0));
            dataAdicionado.setFont(Font.font("Alexandria", 20));
            dataAdicionado.getStyleClass().add("Labels");

            ImageView deletar = new ImageView(new Image(getClass().getResource("/com/example/bookhub/img/deletar.png").toExternalForm()));
            deletar.setFitWidth(42);
            deletar.setFitHeight(42);
            StackPane.setAlignment(deletar, Pos.CENTER_RIGHT);
            StackPane.setMargin(deletar, new Insets(0, 20, 0, 0));

            deletar.getStyleClass().add("botao");

            novoPainelLivro.getChildren().addAll(titulo, autor, dataAdicionado,deletar);

            novoPainelLivro.setOnMouseClicked(event -> consultarInfoLivro(livro));
            deletar.setOnMouseClicked(event -> deletarLivro(lista,livro));


            conteinerLivrosDaLista.getChildren().add(novoPainelLivro);
        }
    }
    public void pesquisarLivro() {
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
    public void consultarInfoLivro(Livro livro) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookhub/views/infoLivro-view.fxml"));
            BorderPane telaInfoLivro = loader.load();

            InfoLivroController infoLivroController = loader.getController();

            infoLivroController.setLivro(livro);

            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.getScene().setRoot(telaInfoLivro);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deletarLivro(Lista lista,Livro livro) {
        ListaDAO listaDAO = new ListaDAO();
        boolean deletacaoValida = listaDAO.retirarLivroLista(lista,livro);

        if(deletacaoValida) {
            Alert confirmacaoCriacaoLista = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacaoCriacaoLista.setTitle("Confirmação");
            confirmacaoCriacaoLista.setHeaderText("LISTA EXCLUIDA COM SUCESSO");
            confirmacaoCriacaoLista.showAndWait();
            carregarLivros(lista);

        } else {
            Alert confirmacaoCriacaoLista = new Alert(Alert.AlertType.ERROR);
            confirmacaoCriacaoLista.setTitle("ERRO");
            confirmacaoCriacaoLista.setHeaderText("ERRO EM EXCLUIR LISTA" + lista.getNome_lista());
            confirmacaoCriacaoLista.showAndWait();
            carregarLivros(lista);
        }
    }


}


