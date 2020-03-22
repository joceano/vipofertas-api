package br.com.vipofertas.service;

import br.com.vipofertas.model.Oferta;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OfertaService {

    ResponseEntity<?> salvar(Oferta oferta);
    ResponseEntity<?> reordenar(List<Oferta> ofertas);
    ResponseEntity<?> excluir(Long id);
    List<Oferta> findByParceiro();
    List<Oferta> findByParceiro(Long id);
    Oferta findByParceiroAndId(Long id);
    Oferta findOneVigente(Long id);
    Oferta findOne(Long id);
}
