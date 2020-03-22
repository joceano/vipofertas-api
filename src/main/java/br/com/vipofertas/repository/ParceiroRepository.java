package br.com.vipofertas.repository;

import br.com.vipofertas.model.Cidade;
import br.com.vipofertas.model.Parceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ParceiroRepository extends JpaRepository<Parceiro, Long>{

    @Query("SELECT e.parceiro "+
           "FROM EnderecoParceiro e "+
           "WHERE e.parceiro.ativo = true AND e.cidade = ?1 ORDER BY e.parceiro.ordem")
    List<Parceiro> findParceiroByCidade(Cidade cidade);
}
