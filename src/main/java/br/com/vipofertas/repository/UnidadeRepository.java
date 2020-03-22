package br.com.vipofertas.repository;

import br.com.vipofertas.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UnidadeRepository extends JpaRepository<Unidade, Long>{

    @Query(value = "SELECT DESCRICAO FROM UNIDADE WHERE UPPER(DESCRICAO) LIKE %?1%", nativeQuery = true)
    List<String> findDescricaoUnidade(String descricao);

    @Query(value = "SELECT DESCRICAO FROM UNIDADE", nativeQuery = true)
    List<String> findDescricao();
}
