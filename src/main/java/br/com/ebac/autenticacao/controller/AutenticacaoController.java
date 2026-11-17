package br.com.ebac.autenticacao.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ebac.autenticacao.dto.Credenciais;
import br.com.ebac.autenticacao.model.Usuario;
import br.com.ebac.autenticacao.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class AutenticacaoController {

    private final UsuarioService service;

    public AutenticacaoController(UsuarioService service) {
        this.service = service;
    }

    // Cadastro: recebe usuário e senha, grava o hash
    @PostMapping("/usuarios")
    public ResponseEntity<Map<String, String>> cadastrar(@RequestBody Credenciais credenciais) {
        Usuario usuario = service.cadastrar(credenciais);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "mensagem", "Usuário cadastrado com sucesso.",
                "usuario", usuario.getUsuario()));
    }

    // Endpoint protegido: só responde 200 se usuário e senha do corpo forem válidos
    @PostMapping("/protegido")
    public ResponseEntity<Map<String, String>> protegido(@RequestBody Credenciais credenciais) {
        Usuario usuario = service.autenticar(credenciais);
        return ResponseEntity.ok(Map.of(
                "mensagem", "Acesso liberado. Bem-vindo, " + usuario.getUsuario() + "!"));
    }
}