package br.com.ebac.autenticacao.exception;

public class SenhaIncorretaException extends RuntimeException {

    public SenhaIncorretaException() {
        super("Senha incorreta.");
    }
}