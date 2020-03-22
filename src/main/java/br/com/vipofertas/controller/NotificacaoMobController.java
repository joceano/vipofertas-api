package br.com.vipofertas.controller;

import br.com.vipofertas.model.Notificacao;
import br.com.vipofertas.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @autor -  Joceano Alves de Borba - <alves.joceano@gmail.com>
 * Controller: NotificacaoMobController, faz o controle de requisições das notificações do Mobile.
 * data: 13/03/2018
 **/
@RestController
@RequestMapping("/notificacaoMob")
public class NotificacaoMobController {

    @Autowired
    NotificacaoService notificacaoService;

    /**
     * Retorna todas as notificações.
     **/
    @GetMapping("/")
    public List<Notificacao> findAll() {
        return notificacaoService.findAll();
    }

    /**
     * Retorna a notificacao pelo código.
     **/
    @GetMapping("/findOne/{id}")
    public Notificacao findOne(@PathVariable Long id) {
        return notificacaoService.findOne(id);
    }
}
