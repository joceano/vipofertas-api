package br.com.vipofertas.service.impl;

import br.com.vipofertas.model.Notificacao;
import br.com.vipofertas.model.Oferta;
import br.com.vipofertas.model.Parceiro;
import br.com.vipofertas.model.Usuario;
import br.com.vipofertas.repository.OfertaRepository;
import br.com.vipofertas.security.service.SecurityService;
import br.com.vipofertas.service.NotificacaoService;
import br.com.vipofertas.service.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

/**
 * @autor -  Joceano Alves de Borba - <alves.joceano@gmail.com>
 * Service: OfertaServiceImpl, regras de negócios das ofertas.
 * data: 25/02/2018
 **/
@Service
public class OfertaServiceImpl implements OfertaService{

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private SecurityService securityService;

    @Value("${imagem.semImagem}")
    private String semImagem;

    /**
     * Salva a oferta
     **/
    @Override
    public ResponseEntity<?> salvar(Oferta oferta) {
        Usuario usuarioLogado = securityService.userLogged();
        oferta.setUsuario(usuarioLogado);
        oferta.setParceiro(usuarioLogado.getParceiro());
        oferta.setDtcriacao(new Date());
        oferta = tratarImagem(oferta);
        oferta = tratarOrdem(oferta);
        Boolean isNotificar = oferta.getIsNotificar();
        oferta = ofertaRepository.save(oferta);
        oferta.setIsNotificar(isNotificar);
        this.notificar(oferta);
        return ResponseEntity.status(HttpStatus.CREATED).body(oferta);
    }

    /**
     * Cria a Notificação ao salvar a oferta
     **/
    private void notificar(Oferta oferta) {
        if ((oferta.getIsNotificar() != null) && (oferta.getIsNotificar()) && oferta.getNotificacoes().isEmpty()) {
            Notificacao notificacao = new Notificacao();
            notificacao.setTitulo(this.montarTituloNotificacao(oferta));
            notificacao.setDescricao(this.montarDescricaoNotificacao(oferta));
            notificacao.setOferta(oferta);
            notificacaoService.notificar(notificacao);
        }
    }

    /**
     * Monta a descrição da Notificação a ser criada ao salvar a oferta
     **/
    private String montarDescricaoNotificacao(Oferta oferta) {
        return oferta.getParceiro().getNome() + ".";
    }

    /**
     * Monta o título da Notificação a ser criada ao salvar a oferta
     **/
    private String montarTituloNotificacao(Oferta oferta) {
        BigDecimal valor = new BigDecimal (oferta.getPrecopromo());
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        String valorFormatado = nf.format (valor);
        return oferta.getNome() + " " + valorFormatado + " " + oferta.getUnidade() + ".";
    }

    /**
     * Define a ordem da oferta.
     **/
    private Oferta tratarOrdem(Oferta oferta) {
        if (oferta.getOrdem() == null) {
            oferta.setOrdem(1L);
            List<Oferta> ofertas = this.findByParceiro();
            if ((ofertas != null) && (ofertas.size() > 0)) {
                oferta.setOrdem(ofertas.get(ofertas.size() - 1).getOrdem() + 1L);
            }
        }
        return oferta;
    }

    /**
     * Seta a imagem default.
     **/
    private Oferta tratarImagem(Oferta oferta) {
        if (oferta.getImagem() == null) {
            oferta.setImagem(semImagem);
        }
        return oferta;
    }

    /**
     * Salva a ordem das ofertas.
     **/
    @Override
    public ResponseEntity<?> reordenar(List<Oferta> ofertas) {
        try {
            for (Oferta oferta : ofertas) {
                ofertaRepository.save(oferta);
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Exclui a oferta
     **/
    @Override
    public ResponseEntity<?> excluir(Long id) {
        ofertaRepository.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Retorna as ofertas do parceiro pelo ID.
     **/
    @Override
    public Oferta findByParceiroAndId(Long id) {
        Usuario usuarioLogado = securityService.userLogged();
        return ofertaRepository.findByParceiroAndId(usuarioLogado.getParceiro(), id);
    }

    /**
     * Retorna as ofertas do parceiro.
     **/
    @Override
    public List<Oferta> findByParceiro() {
        Usuario usuarioLogado = securityService.userLogged();
        return ofertaRepository.findByParceiroOrderByOrdemAsc(usuarioLogado.getParceiro());
    }

    /**
     * Retorna as ofertas do parceiro.
     **/
    @Override
    public List<Oferta> findByParceiro(Long id) {
        Parceiro parceiro = new Parceiro();
        parceiro.setId(id);
        Date dataAtual = new Date();
        return ofertaRepository.findByOfertaMob(parceiro, dataAtual);
    }

    /**
     * Retorna a oferta vigênte pelo código.
     **/
    @Override
    public Oferta findOneVigente(Long id) {
        Date dataAtual = new Date();
        return ofertaRepository.findOneVigente(id, dataAtual);
    }

    /**
     * Retorna a oferta pelo código.
     **/
    @Override
    public Oferta findOne(Long id) {
        return ofertaRepository.findOne(id);
    }
}
