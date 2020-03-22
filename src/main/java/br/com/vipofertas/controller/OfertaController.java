package br.com.vipofertas.controller;

import br.com.vipofertas.model.Oferta;
import br.com.vipofertas.service.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @autor -  Joceano Alves de Borba - <alves.joceano@gmail.com>
 * Controller: OfertaController, faz o controle de requisições das ofertas.
 * data: 25/02/2018
 **/
@RestController
@RequestMapping("/oferta")
public class OfertaController {

    @Autowired
    OfertaService ofertaService;

    /**
     * Retorna as ofertas do parceiro pelo ID.
     **/
    @GetMapping("/{id}")
    public Oferta findOne(@PathVariable Long id) {
        return ofertaService.findByParceiroAndId(id);
    }

    /**
     * Retorna todas as ofertas do parceiro.
     **/
    @GetMapping("/")
    public List<Oferta> findByParceiro() {
        return ofertaService.findByParceiro();
    }

    /**
     * Salva a oferta.
     **/
    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody Oferta oferta){
        return ofertaService.salvar(oferta);
    }

    /**
     * Salva a ordem das ofertas.
     **/
    @PostMapping("/reorder/")
    public ResponseEntity<?> reorder(@RequestBody List<Oferta> ofertas){
        return ofertaService.reordenar(ofertas);
    }

    /**
     * Exclui a oferta.
     **/
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return ofertaService.excluir(id);
    }
}