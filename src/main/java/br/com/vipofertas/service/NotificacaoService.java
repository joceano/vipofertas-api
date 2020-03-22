package br.com.vipofertas.service;

import br.com.vipofertas.model.Notificacao;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NotificacaoService {

    ResponseEntity<?> notificar(Notificacao notificacao);
    List<Notificacao> findByParceiro();
    Notificacao findByParceiroAndId(Long id);
    List<Notificacao> findAll();
    Notificacao findOne(Long id);
}
