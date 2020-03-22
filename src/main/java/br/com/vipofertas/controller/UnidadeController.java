package br.com.vipofertas.controller;

import br.com.vipofertas.model.Unidade;
import br.com.vipofertas.service.UnidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @autor -  Joceano Alves de Borba - <alves.joceano@gmail.com>
 * Controller: UnidadeController, faz o controle de requisições das Unidades de Medida.
 * data: 14/08/2018
 **/

@RestController
@RequestMapping("/unidade")
public class UnidadeController {

    @Autowired
    UnidadeService unidadeService;

    /**
     * Retorna todas as unidades de medida.
     **/
    @GetMapping("/")
    public List<String> findDescricao() {
        return unidadeService.findDescricao();
    }

    /**
     * Retorna todas as unidades de medida com base na descrição.
     **/
    @GetMapping("/{descricao}")
    public List<String> findDescricaoUnidade(@PathVariable String descricao) {
        return unidadeService.findDescricaoUnidade(descricao);
    }
}
