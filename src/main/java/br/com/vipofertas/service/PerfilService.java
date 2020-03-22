package br.com.vipofertas.service;

import br.com.vipofertas.model.PerfilUsuario;
import br.com.vipofertas.model.Usuario;
import org.springframework.http.ResponseEntity;

public interface PerfilService {

    Usuario findByUsuario();
    ResponseEntity<?> salvarDados(PerfilUsuario perfilUsuario);
    ResponseEntity<?> salvarNovaSenha(PerfilUsuario perfilUsuario);
}
