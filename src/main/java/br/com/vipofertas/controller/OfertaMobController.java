package br.com.vipofertas.controller;

import br.com.vipofertas.model.Oferta;
import br.com.vipofertas.service.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @autor -  Joceano Alves de Borba - <alves.joceano@gmail.com>
 * Controller: OfertaMobController, faz o controle de requisições das ofertas do Mobile.
 * data: 25/02/2018
 **/
@RestController
@RequestMapping("/ofertaMob")
public class OfertaMobController {

    @Autowired
    OfertaService ofertaService;

    /**
     * Retorna todas as ofertas do parceiro.
     **/
    @GetMapping("/{idParceiro}")
    public List<Oferta> findByParceiro(@PathVariable Long idParceiro) {
        return ofertaService.findByParceiro(idParceiro);
    }

    /**
     * Retorna a oferta pelo código.
     **/
    @GetMapping("/findOne/{id}")
    public Oferta findOne(@PathVariable Long id) {
        return ofertaService.findOneVigente(id);
    }
}
