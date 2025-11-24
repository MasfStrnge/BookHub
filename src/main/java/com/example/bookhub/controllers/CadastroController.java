package com.example.bookhub.controllers;

import com.example.bookhub.dao.ListaDAO;
import com.example.bookhub.dao.UsuarioDAO;
import com.example.bookhub.models.Lista;
import com.example.bookhub.models.Perfil;
import com.example.bookhub.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class CadastroController {

    public StackPane rootPane;
    @FXML private TextField CampoDeNome;
    @FXML private TextField CampoDeSobrenome;
    @FXML private TextField CampoDeUsuario;
    @FXML private TextField CampoDeEmail;
    @FXML private TextField CampoDeSenha;

    @FXML protected void CriarConta() {
        String nome = this.CampoDeNome.getText().trim();
        String sobrenome = this.CampoDeSobrenome.getText().trim();
        String nomeUsuario = this.CampoDeUsuario.getText().trim();
        String email = this.CampoDeEmail.getText().trim();
        String senha = this.CampoDeSenha.getText().trim();

        if (nome.isEmpty() || sobrenome.isEmpty() || nomeUsuario.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos obrigatórios");
            alert.setHeaderText("Preencha todos os campos!");
            alert.setContentText("Nenhum campo pode ficar em branco.");
            alert.showAndWait();
            return;
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
        boolean cadastroValido = usuarioDAO.criarConta(usuario,perfil,lista);


        if (cadastroValido) {
            CampoDeNome.clear();
            CampoDeSobrenome.clear();
            CampoDeUsuario.clear();
            CampoDeEmail.clear();
            CampoDeSenha.clear();


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Usuário cadastrado");
            alert.setHeaderText("CONTA CRIADA COM SUCESSO!");
            alert.setContentText("Retorne a pagina de login para acessar sua conta.");
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


