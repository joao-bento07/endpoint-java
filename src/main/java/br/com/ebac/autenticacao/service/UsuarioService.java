package br.com.ebac.autenticacao.service;

import java.nio.charset.StandardCharsets;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ebac.autenticacao.dto.Credenciais;
import br.com.ebac.autenticacao.exception.SenhaIncorretaException;
import br.com.ebac.autenticacao.exception.UsuarioJaExisteException;
import br.com.ebac.autenticacao.exception.UsuarioNaoEncontradoException;
import br.com.ebac.autenticacao.model.Usuario;
import br.com.ebac.autenticacao.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    /** Cadastra o usuário gravando apenas o hash BCrypt da senha. */
    @Transactional
    public Usuario cadastrar(Credenciais credenciais) {
        validar(credenciais);
        if (repository.existsByUsuario(credenciais.getUsuario())) {
            throw new UsuarioJaExisteException();
        }
        String hash = passwordEncoder.encode(credenciais.getSenha());
        return repository.save(new Usuario(credenciais.getUsuario(), hash));
    }

    /** Busca o usuário no banco e compara a senha enviada com o hash armazenado. */
    @Transactional(readOnly = true)
    public Usuario autenticar(Credenciais credenciais) {
        validar(credenciais);
        Usuario usuario = repository.findByUsuario(credenciais.getUsuario())
                .orElseThrow(UsuarioNaoEncontradoException::new);

        if (!passwordEncoder.matches(credenciais.getSenha(), usuario.getSenhaHash())) {
            throw new SenhaIncorretaException();
        }
        return usuario;
    }

    private void validar(Credenciais c) {
        if (c == null || c.getUsuario() == null || c.getUsuario().isBlank()
                || c.getSenha() == null || c.getSenha().isBlank()) {
            throw new IllegalArgumentException("Usuário e senha são obrigatórios.");
        }
        // O BCrypt só considera os primeiros 72 bytes da senha
        if (c.getSenha().getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new IllegalArgumentException("A senha deve ter no máximo 72 bytes (limite do BCrypt).");
        }
    }
}