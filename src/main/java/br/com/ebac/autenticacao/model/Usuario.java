package br.com.ebac.autenticacao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String usuario;

    // Guarda o HASH BCrypt (60 caracteres), nunca a senha em texto puro
    @Column(name = "senha_hash", nullable = false, length = 100)
    private String senhaHash;

    protected Usuario() {
        // exigido pelo JPA
    }

    public Usuario(String usuario, String senhaHash) {
        this.usuario = usuario;
        this.senhaHash = senhaHash;
    }

    public Long getId() { return id; }

    public String getUsuario() { return usuario; }

    public String getSenhaHash() { return senhaHash; }
}