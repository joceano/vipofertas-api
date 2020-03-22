package br.com.vipofertas.service;

import br.com.vipofertas.model.Email;
import org.springframework.http.ResponseEntity;

public interface EnviaEmailService {
    void enviar(Email email) throws Exception;
}
