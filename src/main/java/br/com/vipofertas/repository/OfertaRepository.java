package br.com.vipofertas.repository;

import br.com.vipofertas.model.Oferta;
import br.com.vipofertas.model.Parceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OfertaRepository extends JpaRepository<Oferta, Long>{

    @Query("select o from Oferta o where o.ativo = true and o.parceiro = ?1 and "+
           "?2 between o.dtinicial and o.dtfinal ORDER BY o.ordem")
    List<Oferta> findByOfertaMob(Parceiro parceiro, Date dataAtual);

    @Query("select o from Oferta o where o.ativo = true and o.id = ?1 and "+
            "?2 between o.dtinicial and o.dtfinal")
    Oferta findOneVigente(Long id, Date dataAtual);

    List<Oferta> findByParceiroOrderByOrdemAsc(Parceiro parceiro);

    Oferta findByParceiroAndId(Parceiro parceiro, Long id);
}
