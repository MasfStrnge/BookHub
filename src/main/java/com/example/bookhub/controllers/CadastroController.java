package com.example.bookhub.controllers;

import com.example.bookhub.DAO.ListaDAO;
import com.example.bookhub.DAO.UsuarioDAO;
import com.example.bookhub.models.Lista;
import com.example.bookhub.models.Perfil;
import com.example.bookhub.models.Sessao;
import com.example.bookhub.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class CadastroController {

    public StackPane rootPane;
    @FXML private ImageView botaoVoltar;
    @FXML private TextField campoDeNome;
    @FXML private TextField campoDeSobrenome;
    @FXML private TextField campoDeUsuario;
    @FXML private TextField campoDeEmail;
    @FXML private TextField campoDeSenha;


    @FXML private void initialize() {

        botaoVoltar.getStyleClass().add("BotaoVoltar");

    }

    @FXML protected void criarConta() {
        String nome = this.campoDeNome.getText().trim();
        String sobrenome = this.campoDeSobrenome.getText().trim();
        String nomeUsuario = this.campoDeUsuario.getText().trim();
        String email = this.campoDeEmail.getText().trim();
        String senha = this.campoDeSenha.getText().trim();

        if (nome.isEmpty() || sobrenome.isEmpty() || nomeUsuario.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos obrigatórios");
            alert.setHeaderText("Preencha todos os campos!");
            alert.setContentText("Nenhum campo pode ficar em branco.");
            alert.showAndWait();
            return;
        }

        if (nome.length() < 5 || sobrenome.length() < 5) {
            campoDeNome.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            campoDeSobrenome.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nome inválido");
            alert.setHeaderText("Nome e sobrenome devem ter pelo menos 5 caracteres.");
            alert.showAndWait();
            return;
        }

        if (nomeUsuario.length() < 6) {
            campoDeUsuario.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nome de usuário inválido");
            alert.setHeaderText("O nome de usuário deve ter pelo menos 6 caracteres.");
            alert.showAndWait();
            return;
        } else {
            campoDeUsuario.setStyle("");
        }

        if (senha.length() < 6) {
            campoDeSenha.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Senha inválida");
            alert.setHeaderText("A senha deve ter pelo menos 6 caracteres.");
            alert.showAndWait();
            return;
        } else {
            campoDeSenha.setStyle("");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            campoDeEmail.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("E-mail inválido");
            alert.setHeaderText("Digite um e-mail válido (exemplo@dominio.com).");
            alert.showAndWait();
            return;
        } else {
            campoDeEmail.setStyle("");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setSobrenome(sobrenome);
        usuario.setNomeUsuario(nomeUsuario);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Perfil perfil = new Perfil();
        ListaDAO listaDAO = new ListaDAO();
        Lista lista = new Lista();
        boolean cadastroValido = usuarioDAO.criarConta(usuario, perfil, lista);

        if (cadastroValido) {
            campoDeNome.clear();
            campoDeSobrenome.clear();
            campoDeUsuario.clear();
            campoDeEmail.clear();
            campoDeSenha.clear();

            campoDeNome.setStyle("");
            campoDeSobrenome.setStyle("");
            campoDeUsuario.setStyle("");
            campoDeEmail.setStyle("");
            campoDeSenha.setStyle("");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Usuário cadastrado");
            alert.setHeaderText("CONTA CRIADA COM SUCESSO!");
            alert.setContentText("Retorne à página de login para acessar sua conta.");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Atenção");
            alert.setHeaderText("FALHA NA CRIAÇÃO DA CONTA");
            alert.setContentText("Verifique as informações digitadas");
            alert.showAndWait();
        }
    }

    @FXML protected void Voltar(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookhub/views/login-view.fxml"));
            Pane telaLogin = loader.load();

            rootPane.getChildren().setAll(telaLogin);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


