package br.com.vipofertas.controller;

import br.com.vipofertas.model.Notificacao;
import br.com.vipofertas.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @autor -  Joceano Alves de Borba - <alves.joceano@gmail.com>
 * Controller: NotificacaoController, faz o controle de requisições das Notificações.
 * data: 10/03/2018
 **/
@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

    @Autowired
    NotificacaoService notificacaoService;

    /**
     * Retorna as notificações do parceiro pelo ID.
     **/
    @GetMapping("/{id}")
    public Notificacao findOne(@PathVariable Long id) {
        return notificacaoService.findByParceiroAndId(id);
    }

    /**
     * Retorna todas as notificações do parceiro.
     **/
    @GetMapping("/")
    public List<Notificacao> findByParceiro() {
        return notificacaoService.findByParceiro();
    }

    /**
     * Envia a notificação.
     **/
    @PostMapping("/")
    public ResponseEntity<?> notificar(@RequestBody Notificacao notificacao){
        return notificacaoService.notificar(notificacao);
    }
}
