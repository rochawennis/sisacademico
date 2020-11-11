package br.sisacademico.security;

public class Usuario {

    private int idUsuario;
    private String email;
    private String senha;
    private TipoUsuario tipo;

    public Usuario(int idUsuario, String email, String senha, TipoUsuario tipo) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public Usuario() {
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }
}
