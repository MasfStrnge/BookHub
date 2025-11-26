package com.example.bookhub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader; // CARREGA O ARQUIVO fxml
import javafx.scene.Scene; // CRIAR A CENA (JANELA QUE APARECE)
import javafx.stage.Stage; // CRIA O PALCO (OQUE ESTA DENTRO DA TELA)

import java.io.IOException; // OQUE TRATA AS EXCEÇ�ES

public class LoginApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("/com/example/bookhub/views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1440,1024);
        stage.setTitle("BookHub");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
