package br.com.vipofertas.controller;

import br.com.vipofertas.model.PerfilUsuario;
import br.com.vipofertas.model.Usuario;
import br.com.vipofertas.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @autor -  Joceano Alves de Borba - <alves.joceano@gmail.com>
 * Controller: PerfilController, faz o controle de requisições do Perfil do Usuário.
 * data: 25/06  /2018
 **/
@RestController
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    PerfilService perfilService;

    /**
     * Retorna o perfil do usuário Logado.
     **/
    @GetMapping("/")
    public Usuario findOne() {
        return perfilService.findByUsuario();
    }

    /**
     * Salva os dados do perfil do usuário.
     **/
    @PostMapping("/")
    public ResponseEntity<?> salvarDados(@RequestBody PerfilUsuario perfilUsuario){
        return perfilService.salvarDados(perfilUsuario);
    }

    /**
     * Salva os dados da Nova Senha
     **/
    @PostMapping("/novasenha/")
    public ResponseEntity<?> salvarNovaSenha(@RequestBody PerfilUsuario perfilUsuario){
        return perfilService.salvarNovaSenha(perfilUsuario);
    }
}
