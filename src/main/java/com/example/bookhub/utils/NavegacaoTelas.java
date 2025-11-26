package com.example.bookhub.utils;

import com.example.bookhub.models.Sessao;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Optional;

public class NavegacaoTelas {
    public static void trocarTela(Node origem, String caminhoFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(NavegacaoTelas.class.getResource(caminhoFXML));
            Pane novaTela = loader.load();
            Stage stage = (Stage) origem.getScene().getWindow();
            stage.getScene().setRoot(novaTela);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void trocarTelaBorder(Node origem, String caminhoFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(NavegacaoTelas.class.getResource(caminhoFXML));
            BorderPane novaTela = loader.load();
            Stage stage = (Stage) origem.getScene().getWindow();
            stage.getScene().setRoot(novaTela);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logout(Node origem, String caminhoLoginFXML) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmação de Logout");
        alerta.setHeaderText("Tem certeza que deseja sair?");
        alerta.setContentText("Você será desconectada da sua sessão atual.");

        Optional<ButtonType> resultado = alerta.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Sessao.limpar();
            trocarTela(origem, caminhoLoginFXML);
        }
    }
}

