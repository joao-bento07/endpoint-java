package br.com.ebac.autenticacao.dto;

/**
 * Representa o usuário e a senha recebidos no corpo da requisição (JSON).
 * Não há toString() de propósito: evita vazar a senha em logs.
 */
public class Credenciais {

    private String usuario;
    private String senha;

    public Credenciais() {
    }

    public Credenciais(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
