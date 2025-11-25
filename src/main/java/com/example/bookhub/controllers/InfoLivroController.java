package com.example.bookhub.controllers;

import com.example.bookhub.dao.ListaDAO;
import com.example.bookhub.models.*;
import com.example.bookhub.utils.AvaliacaoUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class InfoLivroController {

    @FXML private BorderPane rootPane;
    @FXML private Pane conteinerCapaLivro;
    @FXML private Label tituloLivro, autorLivro, publicacaoLivro,qtPaginasLivro;
    @FXML private ImageView  capaLivro;
    @FXML private TextArea textoDescricao;
    private Livro livro;

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

    public void setLivro(Livro livro) {
        this.livro = livro;
        carregarLivro();
    }

    private void carregarLivro() {
        Usuario usuarioLogado = Sessao.getUsuario();

        tituloLivro.setText(livro.getTitulo());
        autorLivro.setText(livro.getAutor());
        publicacaoLivro.setText("Publicado em: " + String.valueOf(livro.getPublicacao()));
        qtPaginasLivro.setText("Paginas: " + String.valueOf(livro.getQt_pagina()));
        textoDescricao.setText(livro.getDescricao());

        capaLivro.setPreserveRatio(false);
        capaLivro.fitWidthProperty().bind(conteinerCapaLivro.widthProperty());
        capaLivro.fitHeightProperty().bind(conteinerCapaLivro.heightProperty());
        capaLivro.setImage(new Image(getClass().getResource("/com/example/bookhub/" + livro.getCapa()).toExternalForm()));

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(capaLivro.fitWidthProperty());
        clip.heightProperty().bind(capaLivro.fitHeightProperty());
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        capaLivro.setClip(clip);

    }

    public void adicionarLivroLista(Livro livro) {
        Usuario usuarioLogado = Sessao.getUsuario();
        ListaDAO listaDAO = new ListaDAO();

        List<Lista> listas = listaDAO.buscarListasPorPerfil(usuarioLogado.getPerfil().getId_perfil());

        if (listas.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Nenhuma lista encontrada");
            alerta.setHeaderText("Você ainda não possui listas");
            alerta.showAndWait();
            return;
        }

        List<String> nomesListas = listas.stream()
                .map(Lista::getNome_lista)
                .toList();

        ChoiceDialog<String> dialog = new ChoiceDialog<>(nomesListas.get(0), nomesListas);
        dialog.setTitle("Adicionar livro à lista");
        dialog.setHeaderText("Escolha a lista para adicionar o livro");
        dialog.setContentText("Lista:");

        Optional<String> resultado = dialog.showAndWait();

        if (resultado.isPresent()) {
            String nomeListaEscolhida = resultado.get();

            Lista listaEscolhida = listas.stream()
                    .filter(l -> l.getNome_lista().equals(nomeListaEscolhida))
                    .findFirst()
                    .orElse(null);

            if (listaEscolhida != null) {
                StatusDoLivro status;

                switch (nomeListaEscolhida) {
                    case "Favoritos":
                        status = StatusDoLivro.FAVORITO;
                        break;
                    case "Quero Ler":
                        status = StatusDoLivro.QUERO_LER;
                        break;
                    case "Lendo":
                        status = StatusDoLivro.LENDO;
                        break;
                    case "Lidos":
                        status = StatusDoLivro.LIDO;
                        break;
                    default:
                        status = StatusDoLivro.INDEFINIDO;
                }

                boolean sucesso = listaDAO.adicionarLivroLista(listaEscolhida, livro, status, AvaliacaoUI.obterAvaliacaoDoLivro(livro));

                if (sucesso) {
                    Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
                    confirmacao.setTitle("Confirmação");
                    confirmacao.setHeaderText("Livro adicionado com sucesso à lista " + nomeListaEscolhida);
                    confirmacao.showAndWait();
                } else {
                    Alert erro = new Alert(Alert.AlertType.ERROR);
                    erro.setTitle("Erro");
                    erro.setHeaderText("Não foi possível adicionar o livro à lista");
                    erro.showAndWait();
                }
            }
        }
    }

    @FXML private void initialize() {
        Usuario usuarioLogado = Sessao.getUsuario();
    }


    public void adicionarLivroLista(MouseEvent mouseEvent) {
        adicionarLivroLista(livro);
    }
}
