package com.example.bookhub.controllers;

import com.example.bookhub.DAO.ListaDAO;
import com.example.bookhub.DAO.LivroDAO;
import com.example.bookhub.models.*;
import com.example.bookhub.utils.AvaliacaoUI;
import com.example.bookhub.utils.NavegacaoTelas;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.List;
import java.util.Optional;


import javafx.scene.shape.Rectangle;

public class InfoLivroController {

    @FXML private BorderPane rootPane;
    @FXML private Pane conteinerCapaLivro;
    @FXML private Label tituloLivro, autorLivro, publicacaoLivro,qtPaginasLivro;
    @FXML private ImageView  capaLivro;
    @FXML private TextArea textoDescricao;
    private Livro livro;

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

    public void setLivro(Livro livro) {
        this.livro = livro;
        carregarLivro();
    }

    private void carregarLivro() {
        Usuario usuarioLogado = Sessao.getUsuario();

        tituloLivro.setText(livro.getTitulo());
        autorLivro.setText(livro.getAutor());
        publicacaoLivro.setText("Publicado em: " + String.valueOf(livro.getPublicacao()));
        qtPaginasLivro.setText("Páginas " + String.valueOf(livro.getQt_pagina()));
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
                LivroDAO livroDAO = new LivroDAO();

                List<Livro> LivrosExistentesNaLista = livroDAO.buscarLivrosPorLista(listaEscolhida.getId_lista(),listaEscolhida.getNome_lista());
                boolean LivroDuplicado = LivrosExistentesNaLista.stream()
                        .anyMatch(l -> l.getTitulo().equalsIgnoreCase(livro.getTitulo()));

                if (LivroDuplicado) {
                    Alert alertaDuplicado = new Alert(Alert.AlertType.WARNING);
                    alertaDuplicado.setTitle("Aviso - Livro já existente");
                    alertaDuplicado.setHeaderText("Este livro já está na lista " + nomeListaEscolhida + "!");
                    alertaDuplicado.showAndWait();
                    return;
                }

                StatusDoLivro status;
                Avaliacao avaliacao = null;

                switch (nomeListaEscolhida) {
                    case "Favoritos":
                        status = StatusDoLivro.FAVORITO;

                        try {
                            avaliacao = AvaliacaoUI.obterAvaliacaoDoLivro(livro);

                            ListaFavoritos listaFavoritos = new ListaFavoritos(
                                    listaEscolhida.getId_perfil(),
                                    listaEscolhida.getNome_lista(),
                                    listaEscolhida.getQt_livro(),
                                    listaEscolhida.getData_criacao()
                            );

                            ListaLidos listaLidos = ListaLidos.obterListaLidos(usuarioLogado);

                            listaFavoritos.adicionarFavorito(livro, avaliacao);

                            listaDAO.adicionarLivroLista(listaEscolhida, livro, status, avaliacao);

                            listaDAO.adicionarLivroLista(listaLidos, livro, StatusDoLivro.LIDO, avaliacao);

                            Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
                            confirmacao.setTitle("Confirmação");
                            confirmacao.setHeaderText("Livro adicionado com sucesso aos Favoritos e também em Lidos");
                            confirmacao.showAndWait();
                        } catch (IllegalArgumentException e) {
                            Alert erro = new Alert(Alert.AlertType.ERROR);
                            erro.setTitle("Erro");
                            erro.setHeaderText(e.getMessage());
                            erro.showAndWait();
                        }
                        return;

                    case "Quero Ler":
                        ListaQueroLer listaQueroLer = new ListaQueroLer(
                                listaEscolhida.getId_perfil(),
                                listaEscolhida.getNome_lista(),
                                listaEscolhida.getQt_livro(),
                                listaEscolhida.getData_criacao()
                        );

                        listaQueroLer.adicionarQueroLer(livro);
                        status = StatusDoLivro.QUERO_LER;
                        break;

                    case "Lendo":
                        status = StatusDoLivro.LENDO;
                        break;

                    case "Lidos":
                        status = StatusDoLivro.LIDO;

                        try {
                            avaliacao = AvaliacaoUI.obterAvaliacaoDoLivro(livro);
                        } catch (IllegalArgumentException e) {

                            avaliacao = null;
                        }
                        break;

                    default:
                        status = StatusDoLivro.INDEFINIDO;
                }

                boolean sucesso = listaDAO.adicionarLivroLista(listaEscolhida, livro, status, avaliacao);

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
