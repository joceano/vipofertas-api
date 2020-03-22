package br.com.vipofertas.service.impl;

import br.com.vipofertas.model.PerfilUsuario;
import br.com.vipofertas.model.Usuario;
import br.com.vipofertas.repository.UsuarioRepository;
import br.com.vipofertas.security.service.SecurityService;
import br.com.vipofertas.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PerfilServiceImpl implements PerfilService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Usuario findByUsuario() {
        Usuario usuarioLogado = securityService.userLogged();
        return usuarioRepository.findByUsername(usuarioLogado.getUsername());
    }

    @Override
    public ResponseEntity<?> salvarDados(PerfilUsuario perfilUsuario) {
        Usuario usuarioLogado = securityService.userLogged();
        usuarioLogado.setNome(perfilUsuario.getNome());
        usuarioLogado.setSobrenome(perfilUsuario.getSobrenome());
        usuarioLogado.setEmail(perfilUsuario.getEmail());
        usuarioLogado.setNascimento(perfilUsuario.getNascimento());
        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(usuarioLogado));
    }

    @Override
    public ResponseEntity<?> salvarNovaSenha(PerfilUsuario perfilUsuario) {
        /*Válida o conteúdo de todas as informações*/
        if ( (perfilUsuario.getSenhaAtual() == null) ||
             (perfilUsuario.getNovaSenha() == null) ||
             (perfilUsuario.getNovaSenhaRep() == null) ||
             (perfilUsuario.getSenhaAtual().isEmpty()) ||
             (perfilUsuario.getNovaSenha().isEmpty()) ||
             (perfilUsuario.getNovaSenhaRep().isEmpty()) ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Informações faltantes.");
        }

        /*Carrega o usuário logado*/
        Usuario usuarioLogado = securityService.userLogged();

        /*Válida se a senha atual digitada confere*/
        try {
            Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    usuarioLogado.getUsername(), perfilUsuario.getSenhaAtual()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha atual incorreta.");
        }

        /*Válida se a nova senha e a nova senha rep conferem*/
        if (!perfilUsuario.getNovaSenha().equals(perfilUsuario.getNovaSenhaRep())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("As senhas não conferem.");
        }

        /*Válida se a nova senha é diferente da senha atual*/
        if (perfilUsuario.getNovaSenha().equals(perfilUsuario.getSenhaAtual())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A nova senha deve ser diferente a senha atual.");
        }

        usuarioLogado.setPassword(new BCryptPasswordEncoder().encode(perfilUsuario.getNovaSenha()));
        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(usuarioLogado));
    }
}
