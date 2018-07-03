package com.example.acadi.avaclone;

public class Usuario {
    private int Id = 0;
    private String nome;
    private String senha;
    private String email;
    private int CPF;

    public int getId (){
        return Id;
    }
    public void setId(int id){
        Id = id;
    }

    public String getNome() {
        return email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCPF() {
        return CPF;
    }

    public void setCPF(int CPF) {
        this.CPF = CPF;
    }
}
