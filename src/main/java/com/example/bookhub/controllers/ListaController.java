package com.example.bookhub.controllers;

import com.example.bookhub.dao.ListaDAO;
import com.example.bookhub.models.Lista;
import com.example.bookhub.models.Perfil;
import com.example.bookhub.models.Sessao;
import com.example.bookhub.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.awt.*;
import java.io.IOException;
import java.util.Optional;

import javafx.stage.Stage;

public class ListaController {
    @FXML private BorderPane rootPane;
    @FXML private StackPane painelListas;
    @FXML private Pane listaFavoritos, listaLendo, ListaQueroLer, listaLidos; //  Panes definido no FXML
    @FXML private ImageView  botaoPesquisar,botaoPerfil,botaoLogout;
    @FXML private VBox conteinerListas;
    @FXML private Button botaoCriarLista;

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

    @FXML private void initialize() {

        Usuario usuarioLogado = Sessao.getUsuario();
    }

    public void entrarLista(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookhub/views/listaIndividual-view.fxml"));
            BorderPane telaListaIndividual = loader.load();

            Stage stage = (Stage) rootPane.getScene().getWindow();
            ListaIndividualController listaIndividualController = loader.getController();
            stage.getScene().setRoot(telaListaIndividual);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML public void criarLista()  {
       TextInputDialog textInputDialog = new TextInputDialog();
       textInputDialog.setTitle("Nome da nova lista");
       textInputDialog.setHeaderText("Digite o nome da nova lista");
       textInputDialog.setContentText("Nova Lista");

       Optional<String> resultado = textInputDialog.showAndWait();

       if(resultado.isPresent() && !resultado.get().trim().isEmpty()) {
           String nomeNovaLista = resultado.get().trim();
           Lista novaLista = new Lista();
           Perfil perfil = new Perfil();
           novaLista.setNome_lista(nomeNovaLista);

           ListaDAO listaDAO = new ListaDAO();
           boolean criacaoValida = listaDAO.criarLista(novaLista,perfil);

           if(criacaoValida) {
               Alert confirmacaoCriacaoLista = new Alert(Alert.AlertType.CONFIRMATION);
               confirmacaoCriacaoLista.setTitle("Confirmacao");
               confirmacaoCriacaoLista.setHeaderText("LISTA CRIADA COM SUCESSO");
               confirmacaoCriacaoLista.showAndWait();

               Pane novoPainelLista = new Pane();
               Label nomeLista = new Label();
               nomeLista.setText(nomeNovaLista);

               conteinerListas.getChildren().add(novoPainelLista);
           }


       } else {
           System.out.println("erro");
       }

   }


}


