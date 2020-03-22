package br.com.vipofertas.controller;

import br.com.vipofertas.model.Parceiro;
import br.com.vipofertas.service.ParceiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @autor -  Joceano Alves de Borba - <alves.joceano@gmail.com>
 * Controller: ParceiroMobController, faz o controle de requisições dos parceiros do Mobile.
 * data: 25/02/2018
 **/
@RestController
@RequestMapping("/parceiroMob")
public class ParceiroMobController {

    @Autowired
    ParceiroService parceiroService;

    /**
     * Retorna todas as ofertas do parceiro.
     **/
    @GetMapping("/{idCidade}")
    public List<Parceiro> findParceiroByCidade(@PathVariable Long idCidade) {
        return parceiroService.findParceiroByCidade(idCidade);
    }
}
