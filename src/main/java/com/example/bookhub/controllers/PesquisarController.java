package com.example.bookhub.controllers;

import com.example.bookhub.dao.ListaDAO;
import com.example.bookhub.dao.LivroDAO;
import com.example.bookhub.models.*;
import com.example.bookhub.utils.AvaliacaoUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PesquisarController {
    @FXML private BorderPane rootPane;
    @FXML private VBox conteinerLivros;
    @FXML private TextField barraPesquisa;

    @FXML private void initialize() {
        Usuario usuarioLogado = Sessao.getUsuario();
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

    @FXML
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
                Avaliacao avaliacao = null; // agora usa o model Avaliacao

                switch (nomeListaEscolhida) {
                    case "Favoritos":
                        status = StatusDoLivro.FAVORITO;

                        try {
                            // usa AvaliacaoUI para obter avaliação visual
                            avaliacao = AvaliacaoUI.obterAvaliacaoDoLivro(livro);

                            ListaFavoritos listaFavoritos = new ListaFavoritos(
                                    listaEscolhida.getId_perfil(),
                                    listaEscolhida.getNome_lista(),
                                    listaEscolhida.getQt_livro(),
                                    listaEscolhida.getData_criacao()
                            );
                            ListaLidos listaLidos = ListaLidos.obterListaLidos(usuarioLogado);

                            listaFavoritos.adicionarFavorito(livro, avaliacao, listaLidos);

                            listaDAO.adicionarLivroLista(listaEscolhida, livro, status, avaliacao);

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
                        status = StatusDoLivro.QUERO_LER;
                        break;

                    case "Lendo":
                        status = StatusDoLivro.LENDO;
                        break;

                    case "Lidos":
                        status = StatusDoLivro.LIDO;
                        // aqui também usa AvaliacaoUI
                        try {
                            avaliacao = AvaliacaoUI.obterAvaliacaoDoLivro(livro);
                        } catch (IllegalArgumentException e) {
                            // se usuário cancelar, pode deixar null
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

    public void pesquisarLivro() {
        Usuario usuarioLogado = Sessao.getUsuario();
        String termoPesquisa = this.barraPesquisa.getText().trim();

        if (termoPesquisa.isEmpty()) {
            Label vazioLabel = new Label("Realize uma pesquisa para exibirmos os livros");
            vazioLabel.setFont(Font.font("Alexandria", 24));
            vazioLabel.setStyle("-fx-text-fill: rgba(0, 0, 0, 0.56);");
            vazioLabel.setAlignment(Pos.CENTER);
            VBox.setMargin(vazioLabel, new Insets(250, 0, 0, 0));

            conteinerLivros.setAlignment(Pos.CENTER);
            conteinerLivros.getChildren().add(vazioLabel);
            return;
        }

        LivroDAO livroDAO = new LivroDAO();
        List<Livro> livros = livroDAO.exibirLivrosPesquisa(termoPesquisa);

        conteinerLivros.getChildren().clear();

        for (Livro livro : livros) {
            StackPane novoPainelLivro = new StackPane();
            novoPainelLivro.setPrefHeight(224);
            novoPainelLivro.getStyleClass().add("InfoLivro");

            DropShadow sombra = new DropShadow();
            sombra.setColor(Color.color(0, 0, 0, 0.3));
            novoPainelLivro.setEffect(sombra);
            sombra.setOffsetX(0);
            sombra.setOffsetY(4);
            sombra.setBlurType(BlurType.ONE_PASS_BOX);

            Pane conteinerCapaLivro = new Pane();
            conteinerCapaLivro.setMaxHeight(194);
            conteinerCapaLivro.setMaxWidth(144);

            StackPane.setAlignment(conteinerCapaLivro, Pos.CENTER_LEFT);
            StackPane.setMargin(conteinerCapaLivro, new Insets(5,0,5,20));

            ImageView info = new ImageView(new Image(getClass().getResource("/com/example/bookhub/img/info.png").toExternalForm()));
            info.setFitWidth(50);
            info.setFitHeight(50);
            StackPane.setAlignment(info, Pos.TOP_RIGHT);
            StackPane.setMargin(info, new Insets(10,5,0,0));
            info.setOnMouseClicked(event -> consultarInfoLivro(livro));

            ImageView adicionar = new ImageView(new Image(getClass().getResource("/com/example/bookhub/img/adicionar.png").toExternalForm()));
            adicionar.setFitWidth(50);
            adicionar.setFitHeight(50);
            StackPane.setAlignment(adicionar, Pos.TOP_RIGHT);
            StackPane.setMargin(adicionar, new Insets(10,70,0,0));
            adicionar.setOnMouseClicked(event -> adicionarLivroLista(livro));

            info.getStyleClass().add("botao");
            adicionar.getStyleClass().add("botao");

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

            conteinerCapaLivro.getChildren().add(capa);

            Label titulo = new Label(livro.getTitulo());
            titulo.setLayoutX(200);
            titulo.setLayoutY(17);
            titulo.setFont(Font.font("Alexandria", 25));
            titulo.getStyleClass().add("Labels");
            StackPane.setAlignment(titulo,Pos.TOP_LEFT);
            StackPane.setMargin(titulo, new Insets(10,0,0,200));

            Label autor = new Label(livro.getAutor());
            autor.setFont(Font.font("Alexandria", 18));
            autor.getStyleClass().add("Labels");
            StackPane.setAlignment(autor, Pos.TOP_LEFT);
            StackPane.setMargin(autor, new Insets(50,0,0,200));

            Label anoPublicacao = new Label(String.valueOf(livro.getPublicacao()));
            anoPublicacao.setLayoutX(200);
            anoPublicacao.setLayoutY(80);
            anoPublicacao.setFont(Font.font("Alexandria", 18));
            anoPublicacao.getStyleClass().add("Labels");
            StackPane.setAlignment(anoPublicacao, Pos.TOP_LEFT);
            StackPane.setMargin(anoPublicacao, new Insets(90,0,0,200));

            Label genero = new Label(livro.getGenero());
            genero.setLayoutX(200);
            genero.setLayoutY(110);
            genero.setFont(Font.font("Alexandria", 18));
            genero.getStyleClass().add("Labels");
            StackPane.setAlignment(genero, Pos.TOP_LEFT);
            StackPane.setMargin(genero, new Insets(130,0,0,200));

            Label ISBN = new Label("ISBN " + livro.getIsbn());
            ISBN.setLayoutX(200);
            ISBN.setLayoutY(140);
            ISBN.setFont(Font.font("Alexandria", 18));
            ISBN.getStyleClass().add("Labels");
            StackPane.setAlignment(ISBN, Pos.TOP_LEFT);
            StackPane.setMargin(ISBN, new Insets(170,0,0,200));

            novoPainelLivro.getChildren().addAll(conteinerCapaLivro, titulo, autor, anoPublicacao, genero, ISBN, info, adicionar);

            conteinerLivros.getChildren().add(novoPainelLivro);
        }
    }

}
