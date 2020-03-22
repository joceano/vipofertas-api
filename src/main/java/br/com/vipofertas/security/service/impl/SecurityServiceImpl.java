package br.com.vipofertas.security.service.impl;

import br.com.vipofertas.AppConstant;
import br.com.vipofertas.model.Email;
import br.com.vipofertas.model.Response;
import br.com.vipofertas.model.Usuario;
import br.com.vipofertas.repository.UsuarioRepository;
import br.com.vipofertas.security.TokenUtils;
import br.com.vipofertas.security.model.AuthenticationRequest;
import br.com.vipofertas.security.model.AuthenticationResponse;
import br.com.vipofertas.security.service.SecurityService;
import br.com.vipofertas.service.EnviaEmailService;
import br.com.vipofertas.service.impl.UserDetailsServiceImpl;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SecurityServiceImpl implements SecurityService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private EnviaEmailService enviaEmailService;

    @Override
    public Usuario userLogged() {
        String login = null;
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext) {
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication) {
                login = authentication.getName();
            }
        }
        return usuarioRepository.findByUsername(login);
    }

    @Override
    public ResponseEntity<?> authenticationRequest(AuthenticationRequest authenticationRequest) {
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(authenticationRequest.getUsername());
        String token = this.tokenUtils.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @Override
    public ResponseEntity<?> authenticationRequest(HttpServletRequest request) {
        String token = request.getHeader(AppConstant.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(token);
        Usuario user = (Usuario) this.userDetailsServiceImpl.loadUserByUsername(username);
        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())) {
            String refreshedToken = this.tokenUtils.refreshToken(token);
            return ResponseEntity.ok(new AuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Override
    public ResponseEntity<?> redefinirSenha(Usuario usuario) {
        Usuario usuarioBd = usuarioRepository.findByUsernameAndNascimento(usuario.getUsername(), usuario.getNascimento());
        if (usuarioBd == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário não cadastrado!");
        } else {
            if ( (usuarioBd.getEmail() == null) || (usuarioBd.getEmail().isEmpty()) ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email do usuário não cadastrado!");
            } else {
                String novaSenha = RandomStringUtils.random(8, true, true);
                usuarioBd.setPassword(new BCryptPasswordEncoder().encode(novaSenha));
                try {
                    enviaEmailService.enviar(new Email(usuarioBd.getEmail(),
                            "Redefinição de Senha", "Segue sua nova senha: " + novaSenha));
                    usuarioRepository.save(usuarioBd);
                    return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Nova senha enviada para " + usuarioBd.getEmail()));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Não foi possível redefinir a nova senha!");
                }
            }
        }
    }
}
