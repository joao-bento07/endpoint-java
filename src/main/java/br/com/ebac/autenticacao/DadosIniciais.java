package br.com.ebac.autenticacao;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.ebac.autenticacao.dto.Credenciais;
import br.com.ebac.autenticacao.repository.UsuarioRepository;
import br.com.ebac.autenticacao.service.UsuarioService;

@Component
public class DadosIniciais implements CommandLineRunner {

    private final UsuarioService service;
    private final UsuarioRepository repository;

    public DadosIniciais(UsuarioService service, UsuarioRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        service.cadastrar(new Credenciais("admin", "Senha@123"));

        // Prova de que o banco guarda o hash, e não a senha
        String hash = repository.findByUsuario("admin").orElseThrow().getSenhaHash();
        System.out.println("\n>>> Usuário 'admin' criado. Valor gravado no banco: " + hash + "\n");
    }
}
