package com.example.bookhub.models;

public class Usuario {
    private int id_usuario;
    private String nome;
    private String sobrenome;
    private String nomeUsuario;
    private String email;
    private String senha;
    private Perfil perfil;


   public Usuario() {}

   public Usuario(String nome, String sobrenome, String nomeUsuario, String email, String senha, Perfil perfil) {
       this.nome = nome;
       this.sobrenome = sobrenome;
       this.nomeUsuario = nomeUsuario;
       this.email = email;
       this.senha = senha;
       this.perfil = perfil;
   }

    public int getId_usuario() {return id_usuario;}
    public void setId_usuario(int id_usuario) {this.id_usuario = id_usuario;}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome;}

    public String getSobrenome() { return sobrenome;}
    public void setSobrenome(String sobrenome) {this.sobrenome = sobrenome;}

    public String getNomeUsuario() { return nomeUsuario;}
    public void setNomeUsuario(String nomeUsuario) {this.nomeUsuario = nomeUsuario;}

    public String getEmail() { return email;}
    public void setEmail(String email) {this.email = email;}

    public String getSenha() {return senha;}
    public void setSenha(String senha) {this.senha = senha;}

    public Perfil getPerfil() {return perfil;}
    public void setPerfil(Perfil perfil) {this.perfil = perfil;}

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario=" + id_usuario +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", email='" + email + '\'' +
                ", perfil=" + (perfil != null ? perfil.toString() : "null") +
                '}';
    }

}
