package com.example.bookhub.controllers;

import com.example.bookhub.DAO.ListaDAO;
import com.example.bookhub.models.*;
import com.example.bookhub.utils.NavegacaoTelas;
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
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ListaController {
    @FXML private BorderPane rootPane;
    @FXML private VBox conteinerListas;
    @FXML private void botaoPesquisar(MouseEvent mouseEvent) {
        NavegacaoTelas.trocarTelaBorder(rootPane, "/com/example/bookhub/views/pesquisar-view.fxml");
    }
    @FXML private void botaoPerfil(MouseEvent mouseEvent) {
        NavegacaoTelas.trocarTelaBorder(rootPane, "/com/example/bookhub/views/perfil-view.fxml");
    }
    @FXML private void botaoLogout(MouseEvent mouseEvent) {
        NavegacaoTelas.logout(rootPane, "/com/example/bookhub/views/login-view.fxml");
    }


    @FXML private void initialize() {

        Usuario usuarioLogado = Sessao.getUsuario();
        carregarListas();

    }

    public void carregarListas() {
        Usuario usuarioLogado = Sessao.getUsuario();
        conteinerListas.getChildren().clear();

        ListaDAO dao = new ListaDAO();
        List<Lista> listas = dao.buscarListasPorPerfil(usuarioLogado.getPerfil().getId_perfil());

        for (Lista lista : listas) {

            StackPane novoPainelLista = new StackPane();
            novoPainelLista.setPrefHeight(66);
            novoPainelLista.setPrefWidth(1269);

            novoPainelLista.getStyleClass().add("Lista");

            DropShadow sombra = new DropShadow();
            sombra.setColor(Color.color(0, 0, 0, 0.3));
            novoPainelLista.setEffect(sombra);
            sombra.setOffsetX(0);
            sombra.setOffsetY(4);
            sombra.setBlurType(BlurType.ONE_PASS_BOX);

            Label nomeLista = new Label(lista.getNome_lista());
            nomeLista.setFont(Font.font("Alexandria", 25));
            StackPane.setAlignment(nomeLista, Pos.CENTER_LEFT);
            StackPane.setMargin(nomeLista, new Insets(0, 0, 0, 20));
            nomeLista.getStyleClass().add("Labels");

            novoPainelLista.getChildren().add(nomeLista);

            if (lista.getNome_lista().equalsIgnoreCase("Favoritos") ||
                    lista.getNome_lista().equalsIgnoreCase("Lendo") ||
                    lista.getNome_lista().equalsIgnoreCase("Quero Ler") ||
                    lista.getNome_lista().equalsIgnoreCase("Lidos")) {

                novoPainelLista.setStyle("-fx-border-color: #880D0D; -fx-border-width: 2; -fx-border-radius: 5;");
            } else {

                ImageView renomear = new ImageView(new Image(getClass().getResource("/com/example/bookhub/img/renomear.png").toExternalForm()));
                renomear.setFitWidth(42);
                renomear.setFitHeight(42);
                StackPane.setAlignment(renomear, Pos.CENTER_RIGHT);
                StackPane.setMargin(renomear, new Insets(0, 70, 0, 0));

                ImageView deletar = new ImageView(new Image(getClass().getResource("/com/example/bookhub/img/deletar.png").toExternalForm()));
                deletar.setFitWidth(42);
                deletar.setFitHeight(42);
                StackPane.setAlignment(deletar, Pos.CENTER_RIGHT);
                StackPane.setMargin(deletar, new Insets(0, 20, 0, 0));

                renomear.getStyleClass().add("botao");
                deletar.getStyleClass().add("botao");

                novoPainelLista.getChildren().addAll(renomear, deletar);

                renomear.setOnMouseClicked(event -> {renomearLista(lista); event.consume();});
                deletar.setOnMouseClicked(event -> {deletarLista(lista); event.consume();});
            }

            novoPainelLista.setOnMouseClicked(event -> {entrarLista(lista); event.consume();});
            conteinerListas.getChildren().add(novoPainelLista);
        }
    }


    public void entrarLista(Lista lista) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookhub/views/listaIndividual-view.fxml"));
            BorderPane telaListaIndividual = loader.load();

            Stage stage = (Stage) rootPane.getScene().getWindow();
            ListaIndividualController listaIndividualController = loader.getController();

            listaIndividualController.setLista(lista);

            stage.getScene().setRoot(telaListaIndividual);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void criarLista() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Nome da nova lista");
        textInputDialog.setHeaderText("Digite o nome da nova lista");
        textInputDialog.setContentText("Nova Lista");

        Optional<String> resultado = textInputDialog.showAndWait();

        if (resultado.isPresent() && !resultado.get().trim().isEmpty()) {
            String nomeNovaLista = resultado.get().trim();
            Perfil perfil = Sessao.getUsuario().getPerfil();

            ListaDAO listaDAO = new ListaDAO();

            List<String> nomesProibidos = Arrays.asList(
                    "Favoritos", "Lidos", "Lendo", "Quero Ler"
            );

            boolean nomeProibido = nomesProibidos.stream()
                    .anyMatch(n -> n.equalsIgnoreCase(nomeNovaLista));

            if (nomeProibido) {
                Alert alertaProibido = new Alert(Alert.AlertType.WARNING);
                alertaProibido.setTitle("Aviso");
                alertaProibido.setHeaderText("Esse nome não pode ser usado para criar listas!");
                alertaProibido.showAndWait();
                return;
            }

            List<Lista> listasExistentes = listaDAO.buscarListasPorPerfil(perfil.getId_perfil());
            boolean nomeDuplicado = listasExistentes.stream()
                    .anyMatch(l -> l.getNome_lista().equalsIgnoreCase(nomeNovaLista));

            if (nomeDuplicado) {
                Alert alertaDuplicado = new Alert(Alert.AlertType.WARNING);
                alertaDuplicado.setTitle("Aviso");
                alertaDuplicado.setHeaderText("Já existe uma lista com esse nome!");
                alertaDuplicado.showAndWait();
                return;
            }

            Lista novaLista = new Lista();
            novaLista.setNome_lista(nomeNovaLista);

            boolean criacaoValida = listaDAO.criarLista(novaLista, perfil);

            if (criacaoValida) {
                Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
                sucesso.setTitle("Sucesso");
                sucesso.setHeaderText("Lista criada com sucesso!");
                sucesso.showAndWait();
                carregarListas();
            }

        } else {
            System.out.println("erro");
        }
    }

   public void renomearLista(Lista lista) {
       TextInputDialog textInputDialog = new TextInputDialog();
       textInputDialog.setTitle("Renomear Lista");
       textInputDialog.setHeaderText("Digite o novo nome da lista");
       textInputDialog.setContentText("novo nome");

       Optional<String> resultado = textInputDialog.showAndWait();

       if(resultado.isPresent() && !resultado.get().trim().isEmpty()) {
           String novoNomeLista = resultado.get().trim();

           ListaDAO listaDAO = new ListaDAO();
           boolean renomearcaoValida = listaDAO.renomearLista(lista,novoNomeLista);

           System.out.println("Método renomearLista() foi chamado!");

           if(renomearcaoValida) {
               Alert confirmacaoCriacaoLista = new Alert(Alert.AlertType.CONFIRMATION);
               confirmacaoCriacaoLista.setTitle("Confirmação");
               confirmacaoCriacaoLista.setHeaderText("LISTA RENOMEADA COM SUCESSO");
               confirmacaoCriacaoLista.showAndWait();
               lista.setNome_lista(novoNomeLista);
               carregarListas();

           } else {
               Alert confirmacaoCriacaoLista = new Alert(Alert.AlertType.ERROR);
               confirmacaoCriacaoLista.setTitle("ERRO");
               confirmacaoCriacaoLista.setHeaderText("LISTA NAO FOI RENOMEADA");
               confirmacaoCriacaoLista.showAndWait();
               carregarListas();
           }

       }

   }
   public void deletarLista(Lista lista) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação de Exclusão");
        confirmacao.setHeaderText("Deseja realmente excluir a lista \"" + lista.getNome_lista() + "\"?");
        confirmacao.setContentText("Essa ação não poderá ser desfeita.");

        ButtonType botaoSim = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType botaoNao = new ButtonType("Não", ButtonBar.ButtonData.NO);
        confirmacao.getButtonTypes().setAll(botaoSim, botaoNao);

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == botaoSim) {
                ListaDAO listaDAO = new ListaDAO();
                boolean deletacaoValida = listaDAO.deletarLista(lista);

                if (deletacaoValida) {
                    Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
                    sucesso.setTitle("Sucesso");
                    sucesso.setHeaderText("Lista excluída com sucesso!");
                    sucesso.showAndWait();
                    carregarListas();
                } else {
                    Alert erro = new Alert(Alert.AlertType.ERROR);
                    erro.setTitle("Erro");
                    erro.setHeaderText("Erro ao excluir lista " + lista.getNome_lista());
                    erro.showAndWait();
                    carregarListas();
                }
            }
        });
    }




}


