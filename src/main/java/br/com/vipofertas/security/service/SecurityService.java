package br.com.vipofertas.security.service;

import br.com.vipofertas.model.Usuario;
import br.com.vipofertas.security.model.AuthenticationRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {
    Usuario userLogged();
    ResponseEntity<?> authenticationRequest(AuthenticationRequest authenticationRequest);
    ResponseEntity<?> authenticationRequest(HttpServletRequest request);
    ResponseEntity<?> redefinirSenha(Usuario usuario);
}
