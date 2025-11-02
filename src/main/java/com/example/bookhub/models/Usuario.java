package com.example.bookhub.models;

public class Usuario {

    private String nome;
    private String sobrenome;
    private String nomeUsuario;
    private String email;
    private String senha;


   public Usuario() {}

   public Usuario(String nome, String sobrenome, String nomeUsuario, String email, String senha) {
       this.nome = nome;
       this.sobrenome = sobrenome;
       this.nomeUsuario = nomeUsuario;
       this.email = email;
       this.senha = senha;
   }

    public String getNome() { return nome; }
    public void setNome() { this.nome = nome;}

    public String getSobrenome() { return sobrenome;}
    void setSobrenome() {this.sobrenome = sobrenome;}

    public String getNomeUsuario() { return nomeUsuario;}
    void setNomeUsuario() {this.nomeUsuario = nomeUsuario;}

    public String getEmail() { return nomeUsuario;}
    void setEmail() {this.email = email;}

    public String getSenha() {return senha;}
    void setSenha() {this.senha = senha;}
}
