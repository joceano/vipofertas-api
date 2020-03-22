package br.com.vipofertas.security.controller;

import br.com.vipofertas.model.Usuario;
import br.com.vipofertas.security.model.AuthenticationRequest;
import br.com.vipofertas.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private SecurityService securityService;
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest) {
        return securityService.authenticationRequest(authenticationRequest);
    }
	
	@RequestMapping(value = "refresh/", method = RequestMethod.GET)
    public ResponseEntity<?> authenticationRequest(HttpServletRequest request) {
        return securityService.authenticationRequest(request);
    }

    @RequestMapping(value = "logged/", method = RequestMethod.GET)
    public ResponseEntity<?> usuario() {
        return ResponseEntity.status(HttpStatus.OK).body(securityService.userLogged());
    }

    @RequestMapping(value = "redefinir/", method = RequestMethod.POST)
    public ResponseEntity<?> redefinirSenha(@RequestBody Usuario usuario) {
        return securityService.redefinirSenha(usuario);
    }
}
