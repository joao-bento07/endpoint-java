package br.com.ebac.autenticacao.exception;

public class UsuarioJaExisteException extends RuntimeException {

    public UsuarioJaExisteException() {
        super("Usuário já cadastrado.");
    }
}