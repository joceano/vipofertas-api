package br.com.vipofertas.repository;

import br.com.vipofertas.model.Notificacao;
import br.com.vipofertas.model.Parceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long>{

    @Query(value = "SELECT N.* "+
                   "FROM NOTIFICACAO N "+
                   "LEFT JOIN OFERTA O "+
                     "ON O.ID = N.OFERTA_ID "+
                   "WHERE O.ID IS NULL "+
                     "OR O.ATIVO = TRUE "+
                     "AND ?1 BETWEEN O.DTINICIAL AND O.DTFINAL "+
                   "ORDER BY N.ID DESC", nativeQuery = true)
    List<Notificacao> findAllByOrderByIdDesc(Date dataAtual);

    List<Notificacao> findByParceiroOrderByIdDesc(Parceiro parceiro);

    Notificacao findByParceiroAndId(Parceiro parceiro, Long id);
}
