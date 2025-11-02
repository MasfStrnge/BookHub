package com.example.bookhub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader; // CARREGA O ARQUIVO fxml
import javafx.scene.Scene; // CRIAR A CENA (JANELA QUE APARECE)
import javafx.stage.Stage; // CRIA O PALCO (OQUE ESTA DENTRO DA TELA)

import java.io.IOException; // OQUE TRATA AS EXCEÇ�ES

public class LoginApplication extends Application { // CRIA A APLICAÇ�O LoginApplication
    @Override
    public void start(Stage stage) throws IOException {
        // carregando o arquivo login-view.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("/com/example/bookhub/views/login-view.fxml"));
        // Carrega o arquivo para a cena e especifica o tamanho da janela
        Scene scene = new Scene(fxmlLoader.load(),1440,1024);
        // stage coloca um titulo na janela
        stage.setTitle("BookHub - login");
        // stage coloca a cena (login-view.fxml) na janela
        stage.setScene(scene);
        // faz com que a cena aparece na tela
        stage.show();
    }
}
