package br.com.vipofertas.repository;

import br.com.vipofertas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByUsername(String username);
	Usuario findByUsernameAndNascimento(String username, Date nascimento);
	
}
