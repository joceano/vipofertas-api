package br.com.vipofertas.repository;

import br.com.vipofertas.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoRepository extends JpaRepository<Permissao, Long>{
}
